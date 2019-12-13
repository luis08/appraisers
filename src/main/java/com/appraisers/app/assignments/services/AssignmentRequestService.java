package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;

import java.text.ParseException;

public interface AssignmentRequestService {
    AssignmentRequest create(AssignmentRequestDto dto) throws ParseException;
    AssignmentRequest get(Long id);
}
