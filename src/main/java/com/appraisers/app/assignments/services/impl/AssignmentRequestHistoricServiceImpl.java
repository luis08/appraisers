package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestBase;
import com.appraisers.app.assignments.domain.AssignmentRequestMutation;
import com.appraisers.app.assignments.domain.DomainComponent;
import com.appraisers.app.assignments.dto.AssignmentRequestHistoryContainerDto;
import com.appraisers.app.assignments.dto.AssignmentRequestSummaryDto;
import com.appraisers.app.assignments.services.AssignmentRequestHistoricService;
import com.appraisers.app.assignments.services.AssignmentRequestMutationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentRequestHistoricServiceImpl implements AssignmentRequestHistoricService {
    public static final Comparator<AssignmentRequestMutation> HISTORY_REVERSED = Comparator.comparing(AssignmentRequestMutation::getDateCreated).reversed();
    @Autowired
    private AssignmentRequestMutationService assignmentRequestMutationService;


    @Override
    public List<DomainComponent> getUpdates(AssignmentRequest assignmentRequest) {
        List<AssignmentRequestMutation> history = assignmentRequestMutationService.getHistory(assignmentRequest)
                .stream().sorted(HISTORY_REVERSED)
                .collect(Collectors.toList());
        if(history.isEmpty()) {
            return Arrays.asList(assignmentRequest);
        } else {
            return history.stream()
                    .map(p -> (DomainComponent) p)
                    .collect(Collectors.toList());
        }
    }
}
