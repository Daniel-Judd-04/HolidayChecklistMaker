package com.example;

import org.apache.poi.xssf.usermodel.XSSFColor;

public enum ColorTheme {
    HOT(new byte[] {(byte)180, (byte)56, (byte)56}, new byte[] {(byte)204, (byte)85, (byte)0}, new byte[] {(byte)139, (byte)26, (byte)26}),
    WARM(new byte[] {(byte)184, (byte)115, (byte)51}, new byte[] {(byte)255, (byte)140, (byte)0}, new byte[] {(byte)233, (byte)116, (byte)81}),
    MILD(new byte[] {(byte)143, (byte)188, (byte)143}, new byte[] {(byte)85, (byte)107, (byte)47}, new byte[] {(byte)0, (byte)102, (byte)102}),
    COLD(new byte[] {(byte)100, (byte)149, (byte)237}, new byte[] {(byte)70, (byte)130, (byte)180}, new byte[] {(byte)95, (byte)158, (byte)160});

    private final XSSFColor primary;
    private final XSSFColor secondary;
    private final XSSFColor tertiary;

    ColorTheme(byte[] primaryRGB, byte[] secondaryRGB, byte[] tertiaryRGB) {
        this.primary = new XSSFColor(primaryRGB);
        this.secondary = new XSSFColor(secondaryRGB);
        this.tertiary = new XSSFColor(tertiaryRGB);
    }

    public static ColorTheme fromTag(Tag tag) {
        for (ColorTheme theme : ColorTheme.values()) {
            if (theme.name().equals(tag.name())) return theme;
        }
        return ColorTheme.MILD; // Default
    }

    public XSSFColor getPrimary() {
        return primary;
    }

    public XSSFColor getSecondary() {
        return secondary;
    }

    public XSSFColor getTertiary() {
        return tertiary;
    }
}
