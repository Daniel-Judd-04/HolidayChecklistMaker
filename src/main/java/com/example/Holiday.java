package com.example;

import java.util.ArrayList;

public record Holiday(String name, int nights, int smartNights, ArrayList<Tag> tags, ArrayList<ItemSet> participants) {


    @Override
    public String toString() {
        return "NULL";
    }

    /**
     * Removes any extra spaces and capitalises first letters of each word.
     *
     * @return Correctly formatted name.
     */
    public String getName() {
        StringBuilder formattedName = new StringBuilder();
        String[] parts = name.trim().split(" ");
        for (String p : parts) {
            if (!p.isEmpty()) {
                formattedName.append(" ").append(p.toUpperCase().charAt(0));
                if (p.length() > 1) formattedName.append(p.toLowerCase().substring(1));
            }
        }
        return formattedName.toString().trim();
    }

    /**
     * Formats the name of the holiday to work better with file names.
     * E.g. "" to "London_holiday"
     *
     * @return Formatted string
     */
    public String getFileName() {
        return String.join("_", getName().split("[ .]"));
    }

    public Tag get(int i) {
        return tags.get(i);
    }

    public void add(Tag tag) {
        tags.add(tag);
    }

    public void remove(Tag tag) {
        tags.remove(tag);
    }

    public int size() {
        return tags.size();
    }

    public boolean contains(Tag givenTag) {
        return tags.contains(givenTag);
    }

    public boolean contains(ItemSet givenItemSet) {
        for (ItemSet itemSet : participants) {
            if (itemSet.equals(givenItemSet)) return true;
        }
        return false;
    }
}