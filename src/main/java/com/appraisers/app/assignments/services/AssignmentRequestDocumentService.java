package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequest;

public interface AssignmentRequestDocumentService {
    String getDocument(AssignmentRequest assignmentRequest);
}
