package com.appraisers.app.assignments.dto;

import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;

public class AssignmentRequestAttachmentSave {
    private AssignmentRequestAttachment assignmentRequestAttachment;
    private String saveMessage;

    public AssignmentRequestAttachmentSave(AssignmentRequestAttachment assignmentRequestAttachment, String saveMessage) {
        this.assignmentRequestAttachment = assignmentRequestAttachment;
        this.saveMessage = saveMessage;
    }

    public AssignmentRequestAttachment getAssignmentRequestAttachment() {
        return assignmentRequestAttachment;
    }

    public void setAssignmentRequestAttachment(AssignmentRequestAttachment assignmentRequestAttachment) {
        this.assignmentRequestAttachment = assignmentRequestAttachment;
    }

    public String getSaveMessage() {
        return saveMessage;
    }

    public void setSaveMessage(String saveMessage) {
        this.saveMessage = saveMessage;
    }
}
