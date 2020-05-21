package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequestBase;

public interface AssignmentRequestDocumentUploadService {
    String getDocumentUploadedMessage(String document, AssignmentRequestBase assignmentRequestBase, String identifier);
}
