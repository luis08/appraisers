package com.appraisers.storage;

public class OperatingSystemStorageUtils {
    private final static String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();

    public static Platform getPlatform() {
        if (OPERATING_SYSTEM.indexOf("win") >= 0) {
            return Platform.WINDOWS;
        } else if (OPERATING_SYSTEM.indexOf("mac") >= 0) {
            return Platform.MAC;
        } else if ((OPERATING_SYSTEM.indexOf("nix") >= 0 || OPERATING_SYSTEM.indexOf("nux") >= 0 || OPERATING_SYSTEM.indexOf("aix") > 0)) {
            return Platform.UNIX;
        } else {
            return Platform.UNKNOWN;
        }
    }
}
