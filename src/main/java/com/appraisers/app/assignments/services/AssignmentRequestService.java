package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface AssignmentRequestService {
    AssignmentRequest create(AssignmentRequestDto dto) throws Exception;
    Page<AssignmentRequest> findAll(Pageable pageable);
    AssignmentRequest get(Long id);
    AssignmentRequest getByIdentifier(String identifier);
}
