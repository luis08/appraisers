package com.appraisers.app.assignments.dto;

import com.appraisers.app.assignments.domain.DomainComponent;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class AssignmentRequestSummaryDto {

    @Getter
    @Setter
    private Long assignmentRequestId;

    @Getter
    @Setter
    private Long assignmentRequestMutationId;

    @Getter
    @Setter
    private String identifier;

    @Getter
    @Setter
    private String companyName;

    @Getter
    @Setter
    private String accountNumber;

    @Getter
    @Setter
    private String adjusterName;

    @Getter
    @Setter
    private int updateCount;

    @Getter
    @Setter
    private String updateEmail;
    @Getter
    @Setter
    private List<DomainComponent> updates;
}
