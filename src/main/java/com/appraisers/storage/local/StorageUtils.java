package com.appraisers.storage.local;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkState;

public class StorageUtils {

    public static final String FILE_SANITIZE_REGEX = "[^a-zA-Z0-9.-]";
    public static final String INVALID_CHARACTER_REPLACEMENT = "_";

    /**
     * Private constructor. The class is meant for static methods.
     */
    private StorageUtils() {
    }

    public static String sanitizePathComponent(String fileName) {
        checkState(Strings.isNotEmpty(fileName), "The name of a file cannot be null or empty.");
        return fileName.replace(FILE_SANITIZE_REGEX, INVALID_CHARACTER_REPLACEMENT);
    }

    public static String getUniqueFileName(String fullPath) {
        FilenameUtils.getFullPath(fullPath);
        UUID uuid = UUID.randomUUID();
        String extension = FilenameUtils.getExtension(fullPath);
        String workingDirectory = Paths.get("").toAbsolutePath().toString();
        return FilenameUtils.normalize(workingDirectory.concat(File.separator).concat(uuid.toString()).concat(".").concat(extension));
    }
}
