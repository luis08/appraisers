package com.appraisers.app.assignments.dto;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AssignmentRequestHistoryContainerDto {
    @Getter
    @Setter
    private AssignmentRequest assignmentRequest;

    @Getter
    @Setter
    private List<AssignmentRequestSummaryDto> assignmentRequestSummaries;
}
