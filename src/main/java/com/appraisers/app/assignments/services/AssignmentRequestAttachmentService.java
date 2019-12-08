package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AssignmentRequestAttachmentService {
    List<AssignmentRequestAttachment> create(AssignmentRequest request, List<MultipartFile> multipartFile);
    AssignmentRequestAttachment get(Long id);
}
