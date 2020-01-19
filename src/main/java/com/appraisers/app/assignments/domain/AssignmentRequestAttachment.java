package com.appraisers.app.assignments.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assignment_request_attachment")
public class AssignmentRequestAttachment extends DomainComponent {

    @Getter
    @Setter
    private String originalFileName;

    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    private String sanitizedFileName;

    @Getter
    @Setter
    private String fileType;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String storageId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "assignment_request_id")
    private AssignmentRequest assignmentRequest;
}
