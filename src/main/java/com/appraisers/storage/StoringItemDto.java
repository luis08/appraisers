package com.appraisers.storage;

import com.appraisers.storage.local.StorageType;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;


public class StoringItemDto extends StorageItemDto {

    public StoringItemDto(InputStream inputStream, String domainId, StorageType storageType, String originalFileName, String mimeType) {
        super(inputStream);
        this.domainIdentifier = domainId;
        this.storageType = storageType;
        this.originalFileName = originalFileName;
        this.mimeType = mimeType;
    }

    /**
     * We use the domain id for possible storage path construction.
     */
    @Getter
    @Setter
    private String domainIdentifier;

    @Getter
    @Setter
    private StorageType storageType;

    @Getter
    @Setter
    private String originalFileName;

    @Getter
    @Setter
    private String mimeType;
}
