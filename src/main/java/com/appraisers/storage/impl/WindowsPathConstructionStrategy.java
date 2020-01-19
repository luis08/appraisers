package com.appraisers.storage.impl;

import com.appraisers.storage.OperatingSystemStorageUtils;
import com.appraisers.storage.PathConstructionStrategy;
import com.appraisers.storage.Platform;
import com.appraisers.storage.local.StorageType;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class WindowsPathConstructionStrategy implements PathConstructionStrategy {
    @Autowired
    StoragePathService storagePathService;

    @Override
    public boolean isSupported() {
        return OperatingSystemStorageUtils.getPlatform() == Platform.WINDOWS;
    }

    @Override
    public String getFullPath(String domainIdentifier, StorageType storageType, String sanitizedFileName) {
        String homePath = storagePathService.getHomePath();
        String typePath = storagePathService.getStorageTypePath(StorageType.ASSIGNMENT_REQUEST);
        String osPath = Paths.get("").toAbsolutePath().toString();
        String[] path = Arrays.asList(homePath, typePath, domainIdentifier).stream().filter(Strings::isNotEmpty).toArray(String[]::new);
        return Paths.get(osPath, path).toAbsolutePath().toString();
    }
}
