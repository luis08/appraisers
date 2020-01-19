package com.appraisers.storage;

import com.appraisers.storage.dto.PlatformStoringItemDto;

import java.io.IOException;

/**
 * Platform specific implementations of the storage service.  We use properties to specify if it is supported.
 */
public interface PlatformStorageStrategy {
    boolean isSupported();
    StoredItemDto create(PlatformStoringItemDto dto) throws IOException;
    StoredItemDto get(String id);
}
