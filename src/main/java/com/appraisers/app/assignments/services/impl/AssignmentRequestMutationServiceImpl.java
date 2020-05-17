package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.data.AssignmentRequestMutationRepository;
import com.appraisers.app.assignments.data.AssignmentRequestRepository;
import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestMutation;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import com.appraisers.app.assignments.services.AssignmentRequestMutationService;
import com.appraisers.app.assignments.utils.AssignmentUtils;
import com.google.api.client.util.ArrayMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class AssignmentRequestMutationServiceImpl implements AssignmentRequestMutationService {
    @Autowired
    private AssignmentRequestMutationRepository assignmentRequestMutationRepository;

    @Autowired
    private AssignmentRequestRepository assignmentRequestRepository;

    @Override
    public AssignmentRequestMutation save(AssignmentRequestDto assignmentRequestDto, Long id) throws ParseException {
        AssignmentRequest assignmentRequest = assignmentRequestRepository.getOne(id);
        AssignmentRequestMutation assignmentRequestMutation = new AssignmentRequestMutation();
        AssignmentUtils.populate(assignmentRequestDto, assignmentRequestMutation);
        assignmentRequestMutation.setAssignmentRequest(assignmentRequest);
        return assignmentRequestMutationRepository.save(assignmentRequestMutation);
    }

    @Override
    public AssignmentRequestMutation get(Long id) {
        return assignmentRequestMutationRepository.getOne(id);
    }

    @Override
    public AssignmentRequestMutation getLatest(Long assignmentRequestId) {
        AssignmentRequest assignmentRequest = assignmentRequestRepository.getOne(assignmentRequestId);
        Optional<AssignmentRequestMutation> lastMutation = assignmentRequestMutationRepository.findFirstByAssignmentRequestOrderByDateCreatedDesc(assignmentRequest);
        if(lastMutation.isPresent()) {
            return lastMutation.get();
        } else {
            return getNewMutation(assignmentRequest);
        }
    }

    private AssignmentRequestMutation getNewMutation(AssignmentRequest assignmentRequest) {
        AssignmentRequestMutation assignmentRequestMutation = new AssignmentRequestMutation();
        AssignmentUtils.copy(assignmentRequest, assignmentRequestMutation);
        assignmentRequestMutation.setAssignmentRequest(assignmentRequest);

        return assignmentRequestMutationRepository.save(assignmentRequestMutation);
    }
}
