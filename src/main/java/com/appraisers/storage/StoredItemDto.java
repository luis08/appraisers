package com.appraisers.storage;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

public class StoredItemDto extends StorageItemDto {
    public StoredItemDto(InputStream inputStream, String sanitizedFileName, String storageId) {
        super(inputStream);
        this.sanitizedFileName = sanitizedFileName;
        this.storageId = storageId;
    }

    @Getter
    @Setter
    private String sanitizedFileName;

    /**
     * The identifier for the file system. It could be the sanitized file or some platform specific identifier.
     */
    @Getter
    @Setter
    private String storageId;

    @Getter
    @Setter
    private String fullPath;
}
