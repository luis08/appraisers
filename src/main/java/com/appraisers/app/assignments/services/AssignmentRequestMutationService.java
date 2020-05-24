package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestMutation;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;

import java.text.ParseException;
import java.util.List;

public interface AssignmentRequestMutationService {
    AssignmentRequestMutation save(AssignmentRequestDto assignmentRequestDto, Long id) throws ParseException;
    AssignmentRequestMutation get(Long id);
    List<AssignmentRequestMutation> get(List<AssignmentRequest> assignmentRequests);
    AssignmentRequestMutation getLatest(Long assignmentRequestId);
    List<AssignmentRequestMutation> getHistory(AssignmentRequest assignmentRequest);

    List<AssignmentRequestMutation> getAllHistory(List<AssignmentRequest> assignmentRequests);
}
