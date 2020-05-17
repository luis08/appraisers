package com.appraisers.app.assignments.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "assignment_request")
public class AssignmentRequest extends AssignmentRequestBase {

    @Getter
    @Setter
    @Column(name = "identifier")
    private String identifier;

    @Getter
    @Setter
    @OneToMany
    @JoinColumn(name = "assignment_request_id")
    private Set<AssignmentRequestAttachment> attachments;
}
