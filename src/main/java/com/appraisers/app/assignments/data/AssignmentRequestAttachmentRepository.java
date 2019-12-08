package com.appraisers.app.assignments.data;

import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRequestAttachmentRepository extends JpaRepository<AssignmentRequestAttachment, Long> {
}
