package com.example;

public enum Tag {
    ALL,

    // Temp
    HOT,
    WARM,
    MILD,
    COLD,

    // Climate
    WET,
    DAMP,
    MOIST,
    DRY,
    SUNNY,

    // Location
    ABROAD,
    EUROPE,
    UK0,

    // Features
    OUTDOORS,
    CITY,
    BEACH,
    POOL,
    BOAT,

    // Formality
    SMART,
    SMART_CASUAL,
    CASUAL,


    // Special
    SELF_CATERED
    ;

    @Override
    public String toString() {
        StringBuilder name = new StringBuilder("#");
        String[] parts = name().replace("__", "-").split("_");
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (i != 0) name.append("_");

            if (part.contains("0")) name.append(part.replace("0", "").toUpperCase());
            else name.append(part.toUpperCase().charAt(0)).append(part.toLowerCase().substring(1));
        }
        return name.toString();
    }
}
