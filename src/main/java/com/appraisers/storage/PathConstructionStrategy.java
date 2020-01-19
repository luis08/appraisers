package com.appraisers.storage;

import com.appraisers.storage.local.StorageType;

/**
 * Strategies to build paths based on the domain identifier.
 */
public interface PathConstructionStrategy {
    boolean isSupported();
    String getFullPath(String domainIdentifier, StorageType storageType, String sanitizedFileName);
}
