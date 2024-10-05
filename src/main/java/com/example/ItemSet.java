package com.example;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

public enum ItemSet {
    DANIEL,
    LOUIE,
    NEAL,
    DELLA,
    STUFF,
    JOBS;

    private final ArrayList<Item> items = new ArrayList<>();

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItems() {
        for (Item item : Item.values()) {
            if (item.appliesTo(this)) {
                if (this.equals(ItemSet.JOBS) && item.getAppliesTo().length > 1) {
                    boolean aParticipantPresent = false;
                    for (ItemSet itemSet : item.getAppliesTo()) {
                        if (!itemSet.equals(ItemSet.JOBS) && !itemSet.equals(ItemSet.STUFF) && WorkbookExporter.getHoliday().contains(itemSet)) {
                            aParticipantPresent = true;
                            break;
                        }
                    }
                    if (aParticipantPresent) items.add(item);
                } else {
                    items.add(item);
                }
            }
        }
    }

    public void checkItems() {
        for (Item item : items) {
            if (item.isCommon()) try {
                throw new DataFormatException(item + " is common in " + name());
            } catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
