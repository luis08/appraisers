package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestMutation;

public interface AssignmentRequestDocumentService {
    String getDocument(AssignmentRequest assignmentRequest);
    String getDocument(AssignmentRequestMutation assignmentRequestMutation);
}
