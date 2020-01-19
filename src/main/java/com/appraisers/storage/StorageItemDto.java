package com.appraisers.storage;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

public abstract class StorageItemDto {
    public StorageItemDto(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Getter
    @Setter
    private InputStream inputStream;
}
