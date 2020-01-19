package com.appraisers.storage;

public enum Platform {
    WINDOWS("\\"),
    UNIX("/"),
    MAC("/"),
    UNKNOWN(null);

    private final String separator;

    Platform(String sepaator) {
        this.separator = sepaator;
    }

    public static String getSeparator(Platform platform) {
        return platform.separator;
    }
}
