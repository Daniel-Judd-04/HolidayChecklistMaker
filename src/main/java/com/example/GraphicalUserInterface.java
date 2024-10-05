package com.example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class GraphicalUserInterface {
    private static final JFrame frame = new JFrame("Holiday Checklist Creator");

    private static final String fileName = "savedSettings.json";
    private static final String dirName = "HolidayChecklistCreator";

    private static boolean darkMode = false;

    private static JTextField nameInput;
    private static JSpinner nightsInput;
    private static JSpinner smartNightsInput;
    private static JButton openButton;
    private static JLabel submitButtonLabel;
    private static final ArrayList<JCheckBox> tagCheckBoxes = new ArrayList<>();
    private static final ArrayList<JCheckBox> participantCheckBoxes = new ArrayList<>();

    public static void main(String[] args) {

        // Create the main window (JFrame)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 550);

        Holiday holiday = readData(true);
        JPanel formContent = createForm(holiday);
        if (darkMode) {
            frame.setBackground(Color.black);
            formContent.setBackground(Color.black);
            for (Component c : formContent.getComponents()) {
                c.setBackground(Color.black);
            }
        }

        frame.getContentPane().add(formContent, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static JPanel createForm(Holiday holiday) {
        ArrayList<JPanel> elements = new ArrayList<>();

        elements.add(createHeaderButtons());

        elements.add(createInput("Name of Holiday", holiday.name()));

        elements.add(createIntInput("Number of Nights", holiday.nights(), false));

        elements.add(createIntInput("Number of Smart Evenings", holiday.smartNights(), true));

        elements.add(createTagList(holiday.tags()));

        elements.add(createParticipantsList(holiday.participants()));

        elements.add(createSubmitButton());

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (JPanel element : elements) {
            form.add(element);
            form.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return form;
    }

    private static JPanel createHeaderButtons() {
        JButton clearButton = createButton("Clear Form");
        clearButton.addActionListener(e -> clear(false));

        JButton darkModeButton = createButton(darkMode ? "Light Mode" : "Dark Mode");
        darkModeButton.addActionListener(e -> switchColorMode());

        JButton clearAndSaveButton = createButton("Erase Form");
        clearAndSaveButton.addActionListener(e -> clear(true));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        container.add(clearButton);
        container.add(darkModeButton);
        container.add(clearAndSaveButton);

        return container;
    }

    private static void clear(boolean save) {
        // Confirm 'clear & save'
        if (save && (1 == JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear saved settings?\nThis will clear the form and delete the form data.\nThis does not effect previously created excel workbooks.", "Confirm", JOptionPane.YES_NO_OPTION))) {
            return;
        }
        nameInput.setText("");
        nightsInput.setValue(0);
        smartNightsInput.setValue(0);
        for (JCheckBox c : tagCheckBoxes) c.setSelected(false);
        for (JCheckBox c : participantCheckBoxes) c.setSelected(false);

        if (save) {
            saveData(new Holiday("", 0, 0, new ArrayList<>(), new ArrayList<>()));
            JOptionPane.showMessageDialog(frame, "All saved data has been cleared from " + fileName, "Data Cleared", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void switchColorMode() {
        if (JOptionPane.showConfirmDialog(frame, "Restart app to update colour mode?", "Change to " + (!darkMode ? "Dark" : "Light") + " Mode?", JOptionPane.YES_NO_OPTION) == 0) {
            darkMode = !darkMode;
            saveData(readData(false));

            System.exit(0);
        }
    }

    private static JPanel createInput(String labelText, String content) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(createHeading(labelText));

        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(200, 30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setText(content);
        container.add(textField);
        nameInput = textField;

        return container;
    }

    private static JPanel createIntInput(String labelText, int value, boolean smart) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(createHeading(labelText));

        JSpinner input = new JSpinner();
        input.setAlignmentX(Component.CENTER_ALIGNMENT);
        input.setMaximumSize(new Dimension(60, 30));
        input.setValue(value);
        container.add(input);
        if (smart) smartNightsInput = input;
        else nightsInput = input;

        return container;
    }

    private static JPanel createTagList(ArrayList<Tag> tags) {
        JPanel holidayTagsContainer = new JPanel();
        holidayTagsContainer.setLayout(new BoxLayout(holidayTagsContainer, BoxLayout.Y_AXIS));

        JLabel holidayTagsLabel = createHeading("Tags");

        JPanel holidayTags = new JPanel();
        holidayTags.setLayout(new BoxLayout(holidayTags, BoxLayout.X_AXIS));
        if (darkMode) holidayTags.setBackground(Color.black);
        String[] tagLayoutNames = new String[] {"Temperature", "Climate", "Location", "Features", "Formality", "Special"};
        int[] tagLayout = new int[] {4, 5, 3, 5, 3, 1};
        int index = 1;
        for (int i = 0; i < tagLayout.length; i++) {
            int layout = tagLayout[i];
            ArrayList<String> tagNames = new ArrayList<>();
            ArrayList<Boolean> tagValues = new ArrayList<>();
            for (int j = index; j < index+layout ; j++) {
                tagNames.add(Tag.values()[j].toString());
                tagValues.add(tags.contains(Tag.values()[j]));
            }
            JPanel tagsColumn = createCheckBoxArray(tagLayoutNames[i], tagNames, tagValues, BoxLayout.Y_AXIS, tagCheckBoxes);
            tagsColumn.setAlignmentY(Component.TOP_ALIGNMENT);
            holidayTags.add(tagsColumn);
            index += layout;
        }
        holidayTagsContainer.add(holidayTagsLabel);
        holidayTagsContainer.add(holidayTags);
        return holidayTagsContainer;
    }

    private static JPanel createParticipantsList(ArrayList<ItemSet> participants) {
        ArrayList<String> participantNames = new ArrayList<>();
        ArrayList<Boolean> participantValues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            participantNames.add(ItemSet.values()[i].toString());
            participantValues.add(participants.contains(ItemSet.values()[i]));
        }
        return createCheckBoxArray("Participants", participantNames, participantValues, BoxLayout.X_AXIS, participantCheckBoxes);
    }

    private static JPanel createCheckBoxArray(String name, ArrayList<String> names, ArrayList<Boolean> values, int layout, ArrayList<JCheckBox> checkBoxes) {
        if (names.size() != values.size()) return new JPanel();

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        if (layout == BoxLayout.Y_AXIS) mainContainer.add(createSubHeading(name));
        else mainContainer.add(createHeading(name));

        JPanel checkBoxContainer = new JPanel();
        checkBoxContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBoxContainer.setLayout(new BoxLayout(checkBoxContainer, layout));
        for (int i = 0; i < names.size(); i++) {
            JCheckBox checkbox = new JCheckBox(names.get(i));
            if (darkMode) {
                mainContainer.setBackground(Color.black);
                checkBoxContainer.setBackground(Color.black);
                checkbox.setForeground(Color.white);
                checkbox.setBackground(Color.black);
            }
            checkbox.setSelected(values.get(i));
            checkBoxContainer.add(checkbox);
            checkBoxes.add(checkbox);
        }

        mainContainer.add(checkBoxContainer);

        return mainContainer;
    }

    private static JPanel createSubmitButton() {
        JButton submit = createButton("Create & Save");
        submit.addActionListener(e -> submit());

        JButton open = createButton("Open Folder");
        open.setVisible(false);
        open.addActionListener(e -> openFolder());
        openButton = open;

        JLabel label = createLabel("-");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButtonLabel = label;

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.add(submit);
        buttonContainer.add(open);
        buttonContainer.add(label);
        return buttonContainer;
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font newFont = new Font(label.getFont().getFontName(), Font.PLAIN, 14);
        label.setFont(newFont);
        if (darkMode) label.setForeground(Color.white);

        return label;
    }

    private static JLabel createSubHeading(String text) {
        JLabel label = createLabel(text);
        Font newFont = new Font(label.getFont().getFontName(), Font.BOLD, 14);
        label.setFont(newFont);

        return label;
    }

    private static JLabel createHeading(String text) {
        JLabel label = createLabel(text);
        Font newFont = new Font(label.getFont().getFontName(), Font.BOLD, 15);
        label.setFont(newFont);

        return label;
    }

    private static void submit() {
        // Check all inputs are valid
        if (validateInputs()) {
            // Extract input values
            String name = nameInput.getText();
            int nights = (Integer) nightsInput.getValue();
            int smartNights = (Integer) smartNightsInput.getValue();
            ArrayList<Tag> tags = extractTags();
            ArrayList<ItemSet> participants = extractParticipants();

            // Create new holiday and create files
            Holiday holiday = new Holiday(name, nights, smartNights, tags, participants);
            WorkbookExporter.createWorkbook(holiday);

            // Update .json file with current data
            saveData(holiday);

            submitButtonLabel.setVisible(false);
            openButton.setVisible(true);
        }
    }

    private static boolean validateInputs() {
        if (nameInput.getText().isEmpty()) {
            submitButtonLabel.setText("Please enter the name of the holiday.");
            return false;
        }
        if (nightsInput.getValue() instanceof Integer) {
            if((Integer) nightsInput.getValue() <= 0) {
                submitButtonLabel.setText("Please enter the number of nights.");
                return false;
            }
        } else {
            submitButtonLabel.setText("Nights must be a number");
            return false;
        }
        if (!(smartNightsInput.getValue() instanceof Integer)) {
            submitButtonLabel.setText("Smart Evenings must be a number");
            return false;
        }
        if (!validateCheckBoxes(tagCheckBoxes)) {
            submitButtonLabel.setText("Please select at least one tag");
            return false;
        }
        if (!validateCheckBoxes(participantCheckBoxes)) {
            submitButtonLabel.setText("Please select at least one participant");
            return false;
        }

        submitButtonLabel.setText("Loading...");
        return true;
    }

    private static boolean validateCheckBoxes(ArrayList<JCheckBox> checkBoxes) {
        boolean valid = false;
        for (JCheckBox c : checkBoxes) if (c.isSelected()) valid = true;
        return valid;
    }

    private static ArrayList<Tag> extractTags() {
        ArrayList<Tag> selectedTags = new ArrayList<>();
        for (int i = 1; i < Tag.values().length; i++) {
            if (tagCheckBoxes.get(i-1).isSelected()) selectedTags.add(Tag.values()[i]);
        }
        return selectedTags;
    }

    private static ArrayList<ItemSet> extractParticipants() {
        ArrayList<ItemSet> selectedParticipants = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (participantCheckBoxes.get(i).isSelected()) selectedParticipants.add(ItemSet.values()[i]);
        }
        return selectedParticipants;
    }

    private static void saveData(Holiday holiday) {
        JSONObject json = new JSONObject();
        json.put("holidayName", holiday.name());
        json.put("holidayNights", holiday.nights());
        json.put("holidaySmartNights", holiday.smartNights());

        JSONArray jsonTags = new JSONArray();
        for (Tag tag : holiday.tags()) jsonTags.put(tag);
        json.put("holidayTags", jsonTags);

        JSONArray jsonParticipants = new JSONArray();
        for (ItemSet participant : holiday.participants()) jsonParticipants.put(participant);
        json.put("holidayParticipants", jsonParticipants);

        json.put("darkMode", darkMode);

        File distDir = new File(System.getProperty("user.home"), dirName);
        if (!distDir.exists()) distDir.mkdir();

        File file = new File(
                distDir,
                fileName
        );

        // Write the output to the file
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            fileOut.write(json.toString(4).getBytes());
            System.out.println(fileName + " updated successfully at " + file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while updating " + fileName + ":\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Holiday readData(boolean readDarkMode) {
        try (BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/" + dirName + "/" + fileName))) {
            StringBuilder jsonText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) jsonText.append(line);
            JSONObject jsonObject = new JSONObject(jsonText.toString());

            if (readDarkMode && !jsonObject.isNull("darkMode")) darkMode = jsonObject.getBoolean("darkMode");
            String holidayName = "";
            if (!jsonObject.isNull("holidayName")) holidayName = jsonObject.getString("holidayName");
            int holidayNights = 0;
            if (!jsonObject.isNull("holidayNights")) holidayNights = jsonObject.getInt("holidayNights");
            int holidaySmartNights = 0;
            if (!jsonObject.isNull("holidaySmartNights")) holidaySmartNights = jsonObject.getInt("holidaySmartNights");

            ArrayList<Tag> tags = new ArrayList<>();
            if (!jsonObject.isNull("holidayTags")) {
                JSONArray tagsArray = jsonObject.getJSONArray("holidayTags");
                for (int i = 0; i < tagsArray.length(); i++) {
                    tags.add(Tag.valueOf(tagsArray.getString(i)));
                }
            }

            ArrayList<ItemSet> participants = new ArrayList<>();
            if (!jsonObject.isNull("holidayParticipants")) {
                JSONArray participantsArray = jsonObject.getJSONArray("holidayParticipants");
                for (int i = 0; i < participantsArray.length(); i++) {
                    participants.add(ItemSet.valueOf(participantsArray.getString(i)));
                }
            }

            return new Holiday(holidayName, holidayNights, holidaySmartNights, tags, participants);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Unable to read saved data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return new Holiday("", 0, 0, new ArrayList<>(), new ArrayList<>());
    }

    private static void openFolder() {
        // Specify the directory you want to open
        File directory = WorkbookExporter.getParentFolder();

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(directory);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Unable to open directory: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Could not open the folder:\n" + directory + "\nAs this device is not supported.\nHope you can find it on your own!", "Unsupported Device", JOptionPane.WARNING_MESSAGE);
        }
    }
}
