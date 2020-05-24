package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestBase;
import com.appraisers.app.assignments.domain.AssignmentRequestMutation;
import com.appraisers.app.assignments.domain.DomainComponent;
import com.appraisers.app.assignments.dto.AssignmentRequestSummaryDto;
import com.appraisers.app.assignments.services.AssignmentRequestHistoricService;
import com.appraisers.app.assignments.services.AssignmentRequestMutationService;
import com.appraisers.app.assignments.services.AssignmentRequestService;
import com.appraisers.app.assignments.services.AssignmentRequestSummaryService;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssignmentRequestSummaryServiceImpl implements AssignmentRequestSummaryService {
    @Autowired
    private AssignmentRequestService assignmentRequestService;

    @Autowired
    private AssignmentRequestHistoricService assignmentRequestHistoricService;

    @Autowired
    private AssignmentRequestMutationService assignmentRequestMutationService;

    @Override
    public Page<AssignmentRequestSummaryDto> findAll(Pageable pageable) {
        Comparator<AssignmentRequestMutation> latest = Comparator.comparing(AssignmentRequestMutation::getDateCreated);
        Page<AssignmentRequest> assignmentRequestPage = assignmentRequestService.findAll(pageable);
        List<AssignmentRequest> assignmentRequests = assignmentRequestPage.getContent();
        List<AssignmentRequestSummaryDto> dtos = new ArrayList<>();

        Map<Long, List<AssignmentRequestMutation>> allMutations = getMap(assignmentRequests);
        Map<Long, AssignmentRequestMutation> thelatest = allMutations
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        p -> Collections.max(p.getValue(), latest)));
        Map<Long, List<DomainComponent>> allHistory = allMutations
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream().map(m -> (DomainComponent) m).collect(Collectors.toList())
                ));

        assignmentRequests.forEach(ar -> {
            Long assignmentRequestId = ar.getId();
            if (allMutations.containsKey(assignmentRequestId)) {
                AssignmentRequestMutation lastMutation = thelatest.get(assignmentRequestId);
                AssignmentRequestSummaryDto dto = getDtoFromBase(lastMutation);
                dto.setUpdates(allHistory.get(assignmentRequestId));
                dto.setUpdateCount(dto.getUpdates().size());
                dto.setAssignmentRequestMutationId(lastMutation.getId());
                dto.setAssignmentRequestId(assignmentRequestId);
                dto.setUpdateEmail(lastMutation.getUpdateEmail());
                dto.setIdentifier(ar.getIdentifier());
                dtos.add(dto);
            } else {
                dtos.add(getDto(ar));
            }
        });

        return new PageImpl<>(dtos.subList(0, dtos.size() - 1),
                new PageRequest(pageable.getPageNumber(), pageable.getPageSize())
                , assignmentRequestPage.getTotalElements());
    }

    private Map<Long, List<AssignmentRequestMutation>> getMap(List<AssignmentRequest> assignmentRequests) {
        Map<Long, List<AssignmentRequestMutation>> collect = assignmentRequestMutationService.getAllHistory(assignmentRequests).stream().collect(Collectors.groupingBy(m -> m.getAssignmentRequest().getId()));
        return collect;
    }

    private AssignmentRequestSummaryDto getDto(AssignmentRequest assignmentRequest) {
        AssignmentRequestSummaryDto dto = getDtoFromBase(assignmentRequest);
        dto.setUpdates(new ArrayList<>());
        dto.setUpdateCount(0);
        dto.setAssignmentRequestId(assignmentRequest.getId());
        dto.setIdentifier(assignmentRequest.getIdentifier());
        return dto;
    }

    private AssignmentRequestSummaryDto getDtoFromBase(AssignmentRequestBase base) {
        AssignmentRequestSummaryDto assignmentRequestSummaryDto = new AssignmentRequestSummaryDto();
        assignmentRequestSummaryDto.setAccountNumber(base.getAccountNumber());
        assignmentRequestSummaryDto.setCompanyName(base.getCompanyName());
        String adjusterName = getAdjusterName(base);
        if(StringUtils.isNotBlank(adjusterName)){
            assignmentRequestSummaryDto.setAdjusterName(adjusterName);
        }

        return assignmentRequestSummaryDto;
    }

    @Nullable
    private String getAdjusterName(AssignmentRequestBase base) {
        String adjusterFirstName = base.getAdjusterFirstName();
        String adjusterLastName = base.getAdjusterLastName();
        if (StringUtils.isNotBlank(adjusterLastName) && StringUtils.isNotBlank(adjusterFirstName)) {
            return adjusterLastName.concat(", ").concat(adjusterFirstName);
        } else if (StringUtils.isNotBlank(adjusterLastName)) {
            return adjusterLastName;
        } else if (StringUtils.isNotBlank(adjusterFirstName)) {
            return adjusterFirstName;
        }
        return null;
    }
}
