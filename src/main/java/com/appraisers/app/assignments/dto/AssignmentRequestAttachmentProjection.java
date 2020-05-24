package com.appraisers.app.assignments.dto;

import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import com.appraisers.app.assignments.domain.dto.DomainComponentProjection;

public class AssignmentRequestAttachmentProjection extends DomainComponentProjection {
    private AssignmentRequestAttachment component;

    public  AssignmentRequestAttachmentProjection(AssignmentRequestAttachment component) {
        super(component);
        this.component = component;
    }

    public String getOriginalFileName() {
        return component.getOriginalFileName();
    }

    public String getDescription(){
        return component.getDescription();
    }
}
