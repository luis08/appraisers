package com.appraisers.app.resources;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.dto.AssignmentRequestProjection;
import com.appraisers.app.assignments.services.AssignmentRequestService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import org.springframework.web.multipart.MultipartFile;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
public class AssignmentResource {
    private AssignmentRequestService assignmentRequestService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentResource.class);
    @Autowired
    public AssignmentResource(AssignmentRequestService assignmentRequestService) {
        this.assignmentRequestService = assignmentRequestService;
    }
    @PostMapping(value = "/assignment/create", consumes = { "multipart/form-data" })
    public ResponseEntity<AssignmentRequestProjection> saveSplit(@RequestPart(value = "assignmentRequest") AssignmentRequestDto assignmentRequest,
                                                                 @RequestPart("files") MultipartFile[] files) {
        assignmentRequest.setUploadingFiles(files);
        return doSave(assignmentRequest);
    }

    @NotNull
    private ResponseEntity<AssignmentRequestProjection> doSave(AssignmentRequestDto dto) {
        try {
            AssignmentRequestProjection assignmentRequestProjection = new AssignmentRequestProjection(assignmentRequestService.save(dto));
            return ResponseEntity.ok().body(assignmentRequestProjection);
        } catch (Exception e) {
            LOGGER.error("Unable to save assignment request", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/assignment")
    public AssignmentRequestProjection get(Long id) {
        AssignmentRequest assignmentRequest = assignmentRequestService.get(id);
        return new AssignmentRequestProjection(assignmentRequest);
    }
}
