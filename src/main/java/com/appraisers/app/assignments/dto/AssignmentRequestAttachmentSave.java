package com.appraisers.app.assignments.dto;

import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import lombok.Getter;

public class AssignmentRequestAttachmentSave {
    @Getter
    private AssignmentRequestAttachment assignmentRequestAttachment;

    @Getter
    private String saveMessage;

    public AssignmentRequestAttachmentSave(AssignmentRequestAttachment assignmentRequestAttachment, String saveMessage) {
        this.assignmentRequestAttachment = assignmentRequestAttachment;
        this.saveMessage = saveMessage;
    }
}
