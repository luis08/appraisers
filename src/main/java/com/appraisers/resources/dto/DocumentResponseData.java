package com.appraisers.resources.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

import java.io.InputStream;

public class DocumentResponseData {

    public DocumentResponseData(InputStream inputStream, MediaType mediaType) {
        this.inputStream = inputStream;
        this.mediaType = mediaType;
    }

    @Getter
    @Setter
    private MediaType mediaType;

    @Getter
    @Setter
    private InputStream inputStream;
}
