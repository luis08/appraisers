package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.domain.AssignmentRequestBase;
import com.appraisers.app.assignments.dto.GoogleUploadItemDto;
import com.appraisers.app.assignments.services.AssignmentRequestDocumentUploadService;
import com.appraisers.app.gbuckets.GDrive;
import com.appraisers.app.gbuckets.GDriveCommonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Service
public class AssignmentRequestDocumentUploadServiceImpl implements AssignmentRequestDocumentUploadService {
    public static final String INVALID_RESPONSE = "Unable to upload text document with the entire Assignment Request";
    public static final String TEXT_PLAIN = "text/plain";

    @Autowired
    private GDrive googleDrive;

    @Override
    public String getDocumentUploadedMessage(String document, AssignmentRequestBase assignmentRequestBase, String identifier) {
        checkNotNull(document);
        checkNotNull(assignmentRequestBase);
        checkNotNull(identifier);
        checkState(StringUtils.isNotBlank(identifier));

        String fileName = identifier.concat(".txt");
        try {
            GoogleUploadItemDto googleUploadItemDto = new GoogleUploadItemDto(TEXT_PLAIN, fileName, fileName, identifier, document.getBytes());
            GDriveCommonResponse gDriveCommonResponse = googleDrive.uploadFile(googleUploadItemDto);
            if (Objects.isNull(gDriveCommonResponse)) {
                return INVALID_RESPONSE;
            } else {
                return Optional.ofNullable(gDriveCommonResponse.getId()).orElse(INVALID_RESPONSE);
            }
        } catch (JsonProcessingException e) {
            return INVALID_RESPONSE;
        }
    }
}
