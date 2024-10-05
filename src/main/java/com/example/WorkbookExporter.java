package com.example;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class WorkbookExporter {
    private static File parentFolder;
    private static XSSFWorkbook workbook;
    private static Holiday holiday;

    private static boolean printable = false; // false = render both. true = just render printable

    public static void createWorkbook(Holiday newHoliday) {
        parentFolder = null;
        holiday = newHoliday;

        for (ItemSet itemSet : ItemSet.values()) {
            if (itemSet.getItems().isEmpty()) {
                itemSet.addItems(); // Add all items which apply to this set.
                itemSet.checkItems(); // Check that there are no common items.
            }
        }

        // Loop through printable and not
        do {
            workbook = new XSSFWorkbook();
            Styles.setStyles();

            createPersonalSheets(workbook);
            if (!printable) {
                createWholeSheet(workbook);
                createHomeSheet(workbook);
            }

            if (printable) printSetup();
            export();

            printable = !printable;
        } while (printable);
    }

    private static void export() {
        // Main dist folder
        File dir = new File(System.getProperty("user.home") + "/Downloads", "HolidayChecklistCreator");
        if (!dir.exists()) dir.mkdir();

        // Parent folder
        if (parentFolder == null) {
            File parent = new File(dir, holiday.getFileName());
            int v = 1;
            while (parent.exists()) {
                parent = new File(dir, holiday.getFileName() + "_v" + v++);
            }
            parent.mkdir();
            parentFolder = parent;
        }

        String fileName = (printable ? "Printable_" : "") + "Checklist" + ".xlsx";
        // Define the file path
        File file = new File(
                parentFolder,
                fileName
        );

        // Write the output to the file
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
            System.out.println(fileName + " created successfully at " + file.getPath());
        } catch (IOException e) {
            System.out.println("An error occurred while writing the Excel file.");
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printSetup() {
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);

            // Remove all margins
            for (PageMargin pageMargin : PageMargin.values()) sheet.setMargin(pageMargin, 0);

            // Set the print area [B3 to Dx] and center content
            workbook.setPrintArea(sheetIndex, 1, 3, 2, sheet.getLastRowNum());
            sheet.setHorizontallyCenter(true);

            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(false);
            printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);

            // Set the scaling to fit the height (1 page wide)
            printSetup.setFitWidth((short) 0); // 0 means no limit on width
            printSetup.setFitHeight((short) 1);
        }

    }

    private static void createHomeSheet(Workbook workbook) {
        int startingRow = 1;
        int startingCol = 1;

        Sheet sheet = workbook.createSheet(holiday.getName());
        workbook.setSheetOrder(holiday.getName(), 0);

        addHolidayTable(startingRow, startingCol, sheet, false);
        startingRow += 4;

        createStyledMergedArea(sheet, startingRow, 1, startingCol, 3, "Participants", Styles.getHeading());
        for (ItemSet itemSet : holiday.participants()) {
            startingRow++;
            createStyledMergedArea(sheet, startingRow, 1, startingCol, 3, itemSet.toString(), Styles.getBody());
        }
        startingRow += 2;

        createStyledMergedArea(sheet, startingRow, 1, startingCol, 3, "Sheets", Styles.getHeading());
        for (int sheetIndex = 1; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++){
            startingRow++;
            createStyledMergedArea(sheet, startingRow, 1, startingCol, 3, workbook.getSheetName(sheetIndex), Styles.getLink());
            addHyperlink(getCell(sheet, startingRow, startingCol), "'" + workbook.getSheetName(sheetIndex).replace("'", "''") + "'!B6");
        }
        startingRow += 2;

        createStyledMergedArea(sheet, startingRow, 1, startingCol, 3, "Tags", Styles.getHeading());
        for (int i = 0; i < holiday.size(); i++) {
            createStyledMergedArea(sheet, startingRow+i+1, 1, startingCol, 3, holiday.get(i).toString(), Styles.getBody());
        }

        for (int i = 1; i <= 3; i++) sheet.autoSizeColumn(i);
    }

    private static void createStyledMergedArea(Sheet sheet, int startingRow, int height, int startingCol, int width, String cellValue, CellStyle style) {
        getCell(sheet, startingRow, startingCol).setCellValue(cellValue);
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow+height-1, startingCol, startingCol+width-1));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getCell(sheet, startingRow+j, startingCol+i).setCellStyle(style);
            }
        }
    }

    private static Row getRow(Sheet sheet, int rowIndex) {
        if (sheet.getRow(rowIndex) == null) return sheet.createRow(rowIndex);
        return sheet.getRow(rowIndex);
    }

    private static Cell getCell(Sheet sheet, int rowIndex, int cellIndex) {
        Row row = getRow(sheet, rowIndex);
        return getCell(row, cellIndex);
    }

    private static Cell getCell(Row row, int cellIndex) {
        if (row.getCell(cellIndex) == null) return row.createCell(cellIndex);
        return row.getCell(cellIndex);
    }

    private static void addHyperlink(Cell cell, String link) {
        Hyperlink hyperlink = workbook.getCreationHelper().createHyperlink(HyperlinkType.DOCUMENT);
        hyperlink.setAddress(link);
        cell.setHyperlink(hyperlink);
    }

    private static void createWholeSheet(Workbook workbook) {
        int startingRow = 1;
        int startingCol = 1;

        Sheet sheet = workbook.createSheet("Whole List");
        workbook.setSheetOrder("Whole List", 0);

        addHolidayTable(startingRow, startingCol, sheet, true);

        int currentRow = startingRow+4;
        boolean includeHeader = true;
        for (int i = 0; i < ItemSet.values().length; i++) {
            ItemSet itemSet = ItemSet.values()[i];
            if (holiday.contains(itemSet) || itemSet.equals(ItemSet.JOBS) || itemSet.equals(ItemSet.STUFF)) {
                // Add items table
                currentRow = addItemsTable(currentRow, startingCol, sheet, itemSet, includeHeader, true);
                includeHeader = false;
            }
        }

        for (int i = 1; i <= 4; i++) {
            sheet.autoSizeColumn(i);
            if (!printable) sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1024);
        }
    }

    private static void createPersonalSheets(Workbook workbook) {
        int startingRow = 1;
        int startingCol = 1;

        for (ItemSet itemSet : ItemSet.values()) {
            if (holiday.contains(itemSet) || itemSet.equals(ItemSet.JOBS) || itemSet.equals(ItemSet.STUFF)) {
                // Create a new sheet per person
                Sheet sheet = workbook.createSheet(itemSet + (holiday.contains(itemSet) ? "'s List" : ""));

                // Add holiday information table
                addHolidayTable(startingRow, startingCol, sheet, false);

                // Add items table
                addItemsTable(startingRow + 4, startingCol, sheet, itemSet, true, false);

                for (int i = 1; i <= 3; i++) {
                    sheet.autoSizeColumn(i);
                    if (!printable) sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1024);
                }
            }
        }
    }

    private static void addHolidayTable(int startingRow, int startingCol, Sheet sheet, boolean includeName) {
        // Add title
        createStyledMergedArea(sheet, startingRow, 1, startingCol, 3+(includeName?1:0), holiday.getName(), Styles.getSheetTitle());

        // Add nights
        createStyledMergedArea(sheet, startingRow+1, 1, startingCol, 3+(includeName?1:0), holiday.nights() + " Night" + (holiday.nights() == 1? "" : "s"), Styles.getHeading());

        // Add smartNights
        createStyledMergedArea(sheet, startingRow+2, 1, startingCol, 3+(includeName?1:0), holiday.smartNights() + " Smart Night" + (holiday.smartNights() == 1? "" : "s"), Styles.getHeading());
    }

    private static int addItemsTable(int startingRow, int startingCol, Sheet sheet, ItemSet itemSet, boolean includeHeader, boolean includeName) {
        if (includeHeader) {
            // Add title
            createStyledMergedArea(sheet, startingRow, 1, startingCol, 3+(includeName?1:0), sheet.getSheetName(), Styles.getTableTitle());

            // Add table headers
            String[] headers;
            if (includeName) headers = new String[] {"Name","Item", "Amount", "Packed?"};
            else headers = new String[] { "Item", "Amount", "Packed?"};

            for (int i = 0; i < headers.length; i++) {
                getCell(sheet, startingRow + 1, startingCol+i).setCellValue(headers[i]);
                getCell(sheet, startingRow + 1, startingCol+i).setCellStyle(Styles.getHeading());
            }

            if (!printable) sheet.setAutoFilter(CellRangeAddress.valueOf("B7:" + (includeName ? "E" : "D") + "7"));
        }

        ArrayList<Item> recommendedItems = new ArrayList<>();


        // Add common items
        if (holiday.contains(itemSet)) {
            for (Item item : Item.values()) {
                if (item.isCommon() && item.isRecommended()) {
                    recommendedItems.add(item);
                }
            }
        }
        // Add personal/unique items
        for (Item item : itemSet.getItems()) {
            if (item.isRecommended()) {
                recommendedItems.add(item);
            }
        }

        recommendedItems.sort(Item.getNameComparator());
        recommendedItems.sort(Item.getTagComparator());


        // Add items (from this row)
        int rowIndex = startingRow + (includeHeader ? 2 : 0);
        Tag currentTag = includeHeader ? Tag.ALL : null;
        boolean newTag = false;
        for (Item item : recommendedItems) {
            if (item.getLowestRelevantTag() != currentTag) {
                currentTag = item.getLowestRelevantTag();
                newTag = true;
            }
            add(item, sheet, rowIndex, startingCol, includeName ? itemSet : null, newTag);
            newTag = false;
            rowIndex++;
        }
        return rowIndex;
    }

    private static void add(Item item, Sheet sheet, int rowIndex, int startingCell, ItemSet itemSet, boolean newTag) {
        int currentCell = startingCell;
        // Create new row
        Row newRow = getRow(sheet, rowIndex);

        if (itemSet != null) {
            // Add name
            getCell(newRow, 1).setCellValue(itemSet.toString());
            currentCell++;
        }

        // Add item
        getCell(newRow, currentCell).setCellValue(item.getName());
        addComment(item, getCell(newRow, currentCell), sheet);
        currentCell++;

        // Add amount
        String amount = item.formatAmount();
        if (amount.matches("[0-9]+")) {
            int num = Integer.parseInt(amount);
            if (num != 1) getCell(newRow, currentCell).setCellValue(num); // ?
            else getCell(newRow, currentCell).setCellValue(""); // ?
        } else {
            getCell(newRow, currentCell).setCellValue(amount);
        }
        currentCell++;
        for (int i = 1; i <= currentCell; i++) {
            if (newTag) {
                if (i != currentCell) getCell(newRow, i).setCellStyle(Styles.newStyle(11, false, false, HorizontalAlignment.LEFT, Styles.getColorTheme().getPrimary(), new BorderStyle[] {BorderStyle.MEDIUM, BorderStyle.THIN, BorderStyle.THIN, null}));
                else getCell(newRow, i).setCellStyle(Styles.newStyle(11, false, false, HorizontalAlignment.LEFT, Styles.getColorTheme().getPrimary(), new BorderStyle[] {BorderStyle.MEDIUM, null, BorderStyle.THIN, null}));
            } else {
                if (i != currentCell) getCell(newRow, i).setCellStyle(Styles.getTableBody());
                else getCell(newRow, i).setCellStyle(Styles.getBody());
            }
        }
    }

    private static void addComment(Item item, Cell cell, Sheet sheet) {
        ArrayList<StringBuilder> commentRows = new ArrayList<>();

        if (item.getTags().length > 0) {
            StringBuilder newRow = new StringBuilder(item.contains(Tag.ALL) ? "" : "Tags: ");
            Arrays.sort(item.getTags());
            for (Tag tag : item.getTags()) if (holiday.contains(tag)) newRow.append(tag.toString());
            if (!newRow.isEmpty()) commentRows.add(newRow);
        }

        if (item.getRequiredTags().length > 0) {
            StringBuilder newRow = new StringBuilder(item.containsRequired(Tag.ALL) ? "" : "Required Tags: ");
            for (Tag tag : item.getRequiredTags()) if (holiday.contains(tag)) newRow.append(tag.toString().toUpperCase());
            if (!newRow.isEmpty()) commentRows.add(newRow);
        }

        if (item.getRequiredNights() > 0) {
            commentRows.add(new StringBuilder("Minimum Nights: " + item.getRequiredNights()));
        }

        if (item.getRequiredParticipants() > 0) {
            commentRows.add(new StringBuilder("Minimum Participants: " + item.getRequiredParticipants()));
        }

        if (!item.getLowestRelevantTag().equals(Tag.ALL)) {
            commentRows.add(new StringBuilder("Sorted By:" + item.getLowestRelevantTag()));
        }

        StringBuilder commentString = new StringBuilder("Why is this here?");
        int maxRowLength = Integer.MIN_VALUE;
        for (StringBuilder s : commentRows) {
            commentString.append("\n").append(s);
            if (s.length() > maxRowLength) maxRowLength = s.length();
        }
        int columnsNeeded = (int) Math.ceil(maxRowLength / 25.0);

        if (!commentRows.isEmpty()) {
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFComment comment = drawing.createCellComment(drawing.createAnchor(0, 0, 0, 0, 1, 2, 1+columnsNeeded, 3+commentRows.size()));
            comment.setString(new XSSFRichTextString(commentString.toString()));
            comment.setAuthor(item.getName());
            cell.setCellComment(comment);
        }
    }

    public static Holiday getHoliday() {
        return holiday;
    }

    public static XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public static File getParentFolder() {
        return parentFolder;
    }
}


//            "Vancouver Canada",
//                    21,
//                    12,
//                    new Tag[] {Tag.MILD, Tag.WARM, Tag.SUNNY, Tag.DAMP, Tag.MOIST, Tag.ABROAD, Tag.CITY, Tag.RURAL, Tag.POOL, Tag.SMART, Tag.SMART_CASUAL},
//                    new ItemSet[] {ItemSet.DELLA, ItemSet.NEAL, ItemSet.DANIEL, ItemSet.LOUIE}

//"James Boat",
//        4,
//        3,
//        // Temperatures should be ordered with priority for color theme
//        new Tag[] {Tag.HOT, Tag.WARM, Tag.DRY, Tag.ABROAD, Tag.BOAT, Tag.CITY, Tag.SUNNY, Tag.BEACH, Tag.SMART},
//        new ItemSet[] {ItemSet.DELLA, ItemSet.NEAL}

//            "Boogie Town",
//                    3,
//                    1,
//                    // Temperatures should be ordered with priority for color theme
//                    new ArrayList<>(List.of(new Tag[]{Tag.MILD, Tag.DRY, Tag.UK, Tag.RURAL})),
//        new ItemSet[] {ItemSet.DELLA}


