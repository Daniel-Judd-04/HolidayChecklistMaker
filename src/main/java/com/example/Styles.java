package com.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.zip.DataFormatException;

public class Styles {
    private static XSSFCellStyle SHEET_TITLE;
    private static XSSFCellStyle TABLE_TITLE;
    private static XSSFCellStyle HEADING;
    private static XSSFCellStyle BODY;
    private static XSSFCellStyle TABLE_BODY;
    private static XSSFCellStyle LINK;

    private static final ColorTheme colorTheme = setColorTheme();

    private static XSSFWorkbook workbook;

    public Styles() {
    }

    private static Font getFont(int fontSize, boolean bold, boolean underline) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) fontSize);
        font.setBold(bold);
        font.setUnderline(underline ? Font.U_SINGLE : 0);
        return font;
    }

    public static void setStyles() {
        workbook = WorkbookExporter.getWorkbook();
        SHEET_TITLE = workbook.createCellStyle();
        TABLE_TITLE = workbook.createCellStyle();
        HEADING = workbook.createCellStyle();
        BODY = workbook.createCellStyle();
        TABLE_BODY = workbook.createCellStyle();
        LINK = workbook.createCellStyle();

        // SHEET TITLE:
        SHEET_TITLE.setFont(getFont(16, true, true));
        SHEET_TITLE.setAlignment(HorizontalAlignment.CENTER);
        SHEET_TITLE.setVerticalAlignment(VerticalAlignment.CENTER);
        SHEET_TITLE.setBorderBottom(BorderStyle.MEDIUM);
        SHEET_TITLE.setBottomBorderColor(colorTheme.getTertiary());

        // TABLE TITLE:
        TABLE_TITLE.setFont(getFont(14, true, false));
        TABLE_TITLE.setAlignment(HorizontalAlignment.CENTER);
        TABLE_TITLE.setVerticalAlignment(VerticalAlignment.CENTER);
        TABLE_TITLE.setBorderBottom(BorderStyle.MEDIUM);
        TABLE_TITLE.setBottomBorderColor(colorTheme.getSecondary());

        // HEADING:
        HEADING.setFont(getFont(12, true, false));
        HEADING.setAlignment(HorizontalAlignment.CENTER);
        HEADING.setVerticalAlignment(VerticalAlignment.CENTER);
        HEADING.setBorderBottom(BorderStyle.MEDIUM);
        HEADING.setBottomBorderColor(colorTheme.getSecondary());

        // BODY:
        BODY.setFont(getFont(11, false, false));
        BODY.setAlignment(HorizontalAlignment.LEFT);
        BODY.setVerticalAlignment(VerticalAlignment.CENTER);
        BODY.setBorderBottom(BorderStyle.THIN);
        BODY.setBottomBorderColor(colorTheme.getPrimary());

        // TABLE BODY
        TABLE_BODY.setFont(getFont(11, false, false));
        TABLE_BODY.setAlignment(HorizontalAlignment.LEFT);
        TABLE_BODY.setVerticalAlignment(VerticalAlignment.CENTER);
        TABLE_BODY.setBorderRight(BorderStyle.THIN);
        TABLE_BODY.setRightBorderColor(colorTheme.getPrimary());
        TABLE_BODY.setBorderBottom(BorderStyle.THIN);
        TABLE_BODY.setBottomBorderColor(colorTheme.getPrimary());

        // LINK:
        Font font = getFont(11, false, true);
        font.setColor(IndexedColors.BLUE_GREY.getIndex());
        LINK.setFont(font);
        LINK.setAlignment(HorizontalAlignment.LEFT);
        LINK.setVerticalAlignment(VerticalAlignment.CENTER);
        LINK.setBorderBottom(BorderStyle.THIN);
        LINK.setBottomBorderColor(colorTheme.getPrimary());
    }

    private static ColorTheme setColorTheme() {
        Holiday holiday = WorkbookExporter.getHoliday();

        for (Tag tag : holiday.tags()) {
            if (ColorTheme.fromTag(tag) != null) return ColorTheme.fromTag(tag);
        }
        return null;
    }

    public static ColorTheme getColorTheme() {
        return colorTheme;
    }

    public static XSSFCellStyle getSheetTitle() {
        return SHEET_TITLE;
    }

    public static XSSFCellStyle getTableTitle() {
        return TABLE_TITLE;
    }

    public static XSSFCellStyle getHeading() {
        return HEADING;
    }

    public static XSSFCellStyle getBody() {
        return BODY;
    }

    public static XSSFCellStyle getTableBody() {
        return TABLE_BODY;
    }

    public static XSSFCellStyle getLink() {
        return LINK;
    }

    public static XSSFCellStyle newStyle(
            int fontSize,
            boolean isBold,
            boolean isUnderline,
            HorizontalAlignment alignment,
            XSSFColor borderColor,
            BorderStyle[] borderStyles) {

        if (borderStyles.length != 4) try {
            throw new DataFormatException("BorderStyle array had length of " + borderStyles.length + ", when 4 border styles are required.");
        } catch (DataFormatException e) {
            throw new RuntimeException(e);
        }

        XSSFCellStyle newStyle = workbook.createCellStyle();

        newStyle.setFont(getFont(fontSize, isBold, isUnderline));

        newStyle.setAlignment(alignment);
        newStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        if (borderStyles[0] != null) {
            newStyle.setBorderTop(borderStyles[0]);
            newStyle.setTopBorderColor(borderColor);
        }
        if (borderStyles[1] != null) {
            newStyle.setBorderRight(borderStyles[1]);
            newStyle.setRightBorderColor(borderColor);
        }
        if (borderStyles[2] != null) {
            newStyle.setBorderBottom(borderStyles[2]);
            newStyle.setBottomBorderColor(borderColor);
        }
        if (borderStyles[3] != null) {
            newStyle.setBorderLeft(borderStyles[3]);
            newStyle.setLeftBorderColor(borderColor);
        }

        return newStyle;
    }

    public static XSSFCellStyle newStyle(
            int fontSize,
            boolean bold,
            boolean underline,
            HorizontalAlignment alignment,
            XSSFColor borderColor
            ) {
        return newStyle(fontSize, bold, underline, alignment, borderColor, new BorderStyle[] {null, null, null, null});
    }

    public static XSSFCellStyle newStyle(
            int fontSize,
            boolean bold,
            boolean underline,
            HorizontalAlignment alignment,
            XSSFColor borderColor,
            BorderStyle borderStyle
    ) {
        return newStyle(fontSize, bold, underline, alignment, borderColor, new BorderStyle[] {borderStyle, borderStyle, borderStyle, borderStyle});
    }
}
