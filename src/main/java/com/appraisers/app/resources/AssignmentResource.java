package com.appraisers.app.resources;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import com.appraisers.app.assignments.dto.AssignmentRequestProjection;
import com.appraisers.app.assignments.services.AssignmentRequestDocumentService;
import com.appraisers.app.assignments.services.AssignmentRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
public class AssignmentResource {
    private AssignmentRequestService assignmentRequestService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentResource.class);

    @Autowired
    public AssignmentResource(AssignmentRequestService assignmentRequestService) {
        this.assignmentRequestService = assignmentRequestService;
    }

    @Autowired
    private AssignmentRequestDocumentService assignmentRequestDocumentService;

    @PostMapping(value = "/assignment/create", consumes = {"multipart/form-data"})
    public ResponseEntity<AssignmentRequestProjection> saveSplit(@RequestPart(value = "assignmentRequest") AssignmentRequestDto assignmentRequest,
                                                                 @RequestPart("files") MultipartFile[] files) {
        assignmentRequest.setUploadingFiles(files);
        return doSave(assignmentRequest);
    }

    @GetMapping("/assignment/{id}/text")
    public String getForm(@PathVariable Long id) {
        checkNotNull(id);
        AssignmentRequest assignmentRequest = assignmentRequestService.get(id);
        return assignmentRequestDocumentService.getDocument(assignmentRequest);
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<AssignmentRequestProjection> getById(@PathVariable Long id) {
        checkNotNull(id);
        AssignmentRequest assignmentRequest = assignmentRequestService.get(id);
        if(Objects.isNull(assignmentRequest)) {
            return ResponseEntity.notFound().build();
        }
        AssignmentRequestProjection assignmentRequestProjection = new AssignmentRequestProjection(assignmentRequest);
        return ResponseEntity.ok().body(assignmentRequestProjection);
    }

    @GetMapping("/assignments/{identifier}")
    public ResponseEntity<AssignmentRequestProjection> getByIdentifier(@PathVariable String identifier) {
        checkNotNull(identifier);
        AssignmentRequest assignmentRequest = assignmentRequestService.getByIdentifier(identifier);
        if(Objects.isNull(assignmentRequest)) {
            return ResponseEntity.notFound().build();
        }
        AssignmentRequestProjection assignmentRequestProjection = new AssignmentRequestProjection(assignmentRequest);
        return ResponseEntity.ok().body(assignmentRequestProjection);
    }

    @GetMapping("/assignments")
    public Page<AssignmentRequestProjection> get(Pageable pageable) {
        Page<AssignmentRequest> assignmentRequests = assignmentRequestService.findAll(pageable);
        return assignmentRequests.map(AssignmentRequestProjection::new);
    }

    private ResponseEntity<AssignmentRequestProjection> doSave(AssignmentRequestDto dto) {
        try {
            AssignmentRequest assignmentRequest = assignmentRequestService.create(dto);
            AssignmentRequestProjection assignmentRequestProjection = new AssignmentRequestProjection(assignmentRequest);
            return ResponseEntity.ok().body(assignmentRequestProjection);
        } catch (Exception e) {
            LOGGER.error("Unable to save assignment request", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/assignment/{id}")
    public AssignmentRequestProjection get(@PathVariable Long id) {
        AssignmentRequest assignmentRequest = assignmentRequestService.get(id);
        return new AssignmentRequestProjection(assignmentRequest);
    }
}
