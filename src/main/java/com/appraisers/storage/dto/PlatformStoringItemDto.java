package com.appraisers.storage.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

public class PlatformStoringItemDto {
    public PlatformStoringItemDto(InputStream inputStream, String[] path, String sanitizedFileName, String mimeType) {
        this.inputStream = inputStream;
        this.path = path;
        this.sanitizedFileName = sanitizedFileName;
        this.mimeType = mimeType;
    }

    @Getter
    @Setter
    private InputStream inputStream;

    @Getter
    @Setter
    private String[] path;

    @Getter
    @Setter
    private String sanitizedFileName;

    @Getter
    @Setter
    private String mimeType;
}
