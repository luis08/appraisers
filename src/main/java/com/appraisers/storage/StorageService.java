package com.appraisers.storage;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.Optional;

/**
 * This service is meant to be only for storage purposes.  The
 */
public interface StorageService {

    /**
     * @param storingItemDto the {@link StoringItemDto}
     * @return the {@link StoringItemDto}
     */
    Optional<StoredItemDto> create(StoringItemDto storingItemDto) throws NotSupportedException, IOException;

    /**
     * @param id the {@link String} id
     * @return the {@link StoredItemDto} for the file
     */
    Optional<StoredItemDto> get(String id) throws NotSupportedException;
}
