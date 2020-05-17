package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequestMutation;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;

import java.text.ParseException;

public interface AssignmentRequestMutationService {
    AssignmentRequestMutation save(AssignmentRequestDto assignmentRequestDto, Long id) throws ParseException;
    AssignmentRequestMutation get(Long id);
    AssignmentRequestMutation getLatest(Long assignmentRequestId);
}
