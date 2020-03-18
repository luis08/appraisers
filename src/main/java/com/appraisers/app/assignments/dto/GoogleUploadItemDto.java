package com.appraisers.app.assignments.dto;

import lombok.Getter;

public class GoogleUploadItemDto {
    @Getter
    private String contentType;

    @Getter
    private String originalFilename;

    @Getter
    private byte[] byteArray;

    @Getter
    private String fileName;

    @Getter
    private String folderName;

    public GoogleUploadItemDto(String contentType, String originalFilename, String fileName, String folderName, byte[] byteArray) {
        this.contentType = contentType;
        this.originalFilename = originalFilename;
        this.fileName = fileName;
        this.folderName = folderName;
        this.byteArray = byteArray;
    }
}
