package com.appraisers.app.assignments.dto;

import lombok.Getter;
import lombok.Setter;

public class AssignmentRequestMutationDto extends AssignmentRequestBaseDto {

    @Getter
    @Setter
    private AssignmentRequestDto assignmentRequest;
}
