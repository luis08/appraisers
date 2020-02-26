package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import com.appraisers.app.assignments.dto.AssignmentRequestAttachmentSave;
import com.appraisers.resources.dto.DocumentResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AssignmentRequestAttachmentService {
    List<AssignmentRequestAttachmentSave> create(AssignmentRequest request, List<MultipartFile> multipartFile) throws Exception;
    AssignmentRequestAttachment get(Long id);
    Optional<DocumentResponseData> getResponseData(Long id);
}
