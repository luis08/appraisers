package com.appraisers.app.assignments.data;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRequestAttachmentRepository extends JpaRepository<AssignmentRequestAttachment, Long> {
    List<AssignmentRequestAttachment> findAllByAssignmentRequest(AssignmentRequest assignmentRequest);
}
