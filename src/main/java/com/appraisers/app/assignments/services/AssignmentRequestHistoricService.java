package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.DomainComponent;

import java.util.List;

public interface AssignmentRequestHistoricService {
    List<DomainComponent> getUpdates(AssignmentRequest assignmentRequest);

}
