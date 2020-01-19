package com.appraisers.storage.impl;

import com.appraisers.storage.PathConstructionStrategy;
import com.appraisers.storage.local.StorageType;

public class GooglePathConstructionStrategy implements PathConstructionStrategy {
    @Override
    public boolean isSupported() {
        return false;
    }

    @Override
    public String getFullPath(String domainIdentifier, StorageType storageType, String sanitizedFileName) {
        return null;
    }
}
