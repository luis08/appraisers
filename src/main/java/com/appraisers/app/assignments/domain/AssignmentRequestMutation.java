package com.appraisers.app.assignments.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "assignment_request_mutation")
public class AssignmentRequestMutation extends AssignmentRequestBase {

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "assignment_request_id")
    private AssignmentRequest assignmentRequest;
}
