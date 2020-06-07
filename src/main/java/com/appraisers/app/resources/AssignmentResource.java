package com.appraisers.app.resources;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestMutation;
import com.appraisers.app.assignments.domain.DomainComponent;
import com.appraisers.app.assignments.domain.dto.DomainComponentProjection;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import com.appraisers.app.assignments.dto.AssignmentRequestMutationProjection;
import com.appraisers.app.assignments.dto.AssignmentRequestProjection;
import com.appraisers.app.assignments.dto.AssignmentRequestSummaryDto;
import com.appraisers.app.assignments.services.*;
import com.appraisers.app.gmail.GmailBuilderService;
import com.appraisers.app.gmail.GmailService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
public class AssignmentResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentResource.class);
    private static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String ATTACHMENTS_ARE_SAVED_SEPARATELY = "Attachments are saved separately.";

    private final AssignmentRequestService assignmentRequestService;
    private final AssignmentRequestHistoricService assignmentRequestHistoricService;
    private final AssignmentRequestSummaryService assignmentRequestSummaryService;
    private AssignmentRequestDocumentService assignmentRequestDocumentService;
    private AssignmentRequestMutationService assignmentRequestMutationService;
    private GmailBuilderService gmailBuilderService;
    private GmailService gmailService;
    private AssignmentRequestDocumentUploadService assignmentRequestDocumentUploadService;

    @Autowired
    public AssignmentResource(AssignmentRequestService assignmentRequestService,
                              AssignmentRequestMutationService assignmentRequestMutationService,
                              AssignmentRequestDocumentService assignmentRequestDocumentService,
                              AssignmentRequestHistoricService assignmentRequestHistoricService,
                              AssignmentRequestSummaryService assignmentRequestSummaryService,
                              AssignmentRequestDocumentUploadService assignmentRequestDocumentUploadService,
                              GmailBuilderService gmailBuilderService,
                              GmailService gmailService
                              ) {
        this.assignmentRequestMutationService = assignmentRequestMutationService;
        this.assignmentRequestDocumentService = assignmentRequestDocumentService;
        this.assignmentRequestService = assignmentRequestService;
        this.assignmentRequestHistoricService = assignmentRequestHistoricService;
        this.assignmentRequestSummaryService = assignmentRequestSummaryService;
        this.gmailBuilderService = gmailBuilderService;
        this.gmailService = gmailService;
        this.assignmentRequestDocumentUploadService = assignmentRequestDocumentUploadService;
    }

    @PostMapping(value = "/assignment/create", consumes = {"multipart/form-data"})
    public ResponseEntity<AssignmentRequestProjection> saveSplit(@RequestPart(value = "assignmentRequest") AssignmentRequestDto assignmentRequest,
                                                                 @RequestPart("files") MultipartFile[] files) {
        assignmentRequest.setUploadingFiles(files);
        return doSave(assignmentRequest);
    }

    @GetMapping("/assignment/latest/{id}")
    public ResponseEntity<AssignmentRequestMutationProjection> getAssignmentRequestMutation(@PathVariable Long id) {
        return getLatest(id);
    }

    @NotNull
    private ResponseEntity<AssignmentRequestMutationProjection> getLatest(Long assignmentRequestId) {
        AssignmentRequestMutation assignmentRequestMutation = assignmentRequestMutationService.getLatest(assignmentRequestId);
        if (Objects.isNull(assignmentRequestMutation)) {
            return ResponseEntity.notFound().build();
        } else {
            AssignmentRequestMutationProjection assignmentRequestMutationProjection = new AssignmentRequestMutationProjection(assignmentRequestMutation);
            return ResponseEntity.ok().body(assignmentRequestMutationProjection);
        }
    }

    @PostMapping(value = "/assignment/{assignmentRequestId}/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AssignmentRequestMutationProjection> update(@PathVariable(value = "assignmentRequestId") Long assignmentRequestId,
                                                                      @RequestBody AssignmentRequestDto assignmentRequestDto,
                                                                      @RequestParam(value = "sendUpdateEmail", defaultValue = "false") boolean sendUpdateEmail) {
        try {
            AssignmentRequestMutation assignmentRequestMutation = assignmentRequestMutationService.save(assignmentRequestDto, assignmentRequestId);
            if (sendUpdateEmail) {
                String identifier = assignmentRequestMutation.getAssignmentRequest().getIdentifier();
                String document = assignmentRequestDocumentService.getDocument(assignmentRequestMutation)
                        .concat(LINE_SEPARATOR)
                        .concat(ATTACHMENTS_ARE_SAVED_SEPARATELY);
                String documentSavedMessage = assignmentRequestDocumentUploadService.getDocumentUploadedMessage(document, assignmentRequestMutation, identifier);
                document = document.concat(LINE_SEPARATOR)
                        .concat(LINE_SEPARATOR)
                        .concat(documentSavedMessage);

                sendEmail(document, identifier, assignmentRequestDto.getUpdateEmail());
            }
            return ResponseEntity.ok().body(new AssignmentRequestMutationProjection(assignmentRequestMutation));
        } catch (ParseException e) {
            LOGGER.error("Unable to save assignment request mutation", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/assignment/{id}/text", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getForm(@PathVariable Long id) {
        checkNotNull(id);
        AssignmentRequest assignmentRequest = assignmentRequestService.get(id);
        return assignmentRequestDocumentService.getDocument(assignmentRequest);
    }

    @GetMapping(value = "/assignment/mutation/{id}/text", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getUpdateForm(@PathVariable Long id) {
        checkNotNull(id);
        AssignmentRequestMutation assignmentRequestMutation = assignmentRequestMutationService.get(id);
        return assignmentRequestDocumentService.getDocument(assignmentRequestMutation);
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<AssignmentRequestProjection> getById(@PathVariable Long id) {
        checkNotNull(id);
        AssignmentRequest assignmentRequest = assignmentRequestService.get(id);
        if (Objects.isNull(assignmentRequest)) {
            return ResponseEntity.notFound().build();
        }
        AssignmentRequestProjection assignmentRequestProjection = new AssignmentRequestProjection(assignmentRequest);
        return ResponseEntity.ok().body(assignmentRequestProjection);
    }

    @GetMapping("/assignments/identifier/{identifier}")
    public ResponseEntity<AssignmentRequestMutationProjection> getByIdentifier(@PathVariable String identifier) {
        checkNotNull(identifier);
        AssignmentRequest assignmentRequest = assignmentRequestService.getByIdentifier(identifier);

        if (Objects.isNull(assignmentRequest)) {
            return ResponseEntity.notFound().build();
        }
        return getLatest(assignmentRequest.getId());
    }

    @GetMapping("/assignments")
    public Page<AssignmentRequestProjection> get(Pageable pageable) {
        Page<AssignmentRequest> assignmentRequests = assignmentRequestService.findAll(pageable);
        return assignmentRequests.map(AssignmentRequestProjection::new);
    }

    @GetMapping("/assignments/{id}/history")
    public List<DomainComponentProjection> getUpdateHistory(@PathVariable Long id) {
        checkNotNull(id);
        AssignmentRequest assignmentRequest = assignmentRequestService.get(id);
        List<DomainComponent> updates = assignmentRequestHistoricService.getUpdates(assignmentRequest);
        return updates.stream().map(c -> new DomainComponentProjection(c))
                .collect(Collectors.toList());
    }

    @GetMapping("/assignments/summaries")
    public Page<AssignmentRequestSummaryDto> getSummaries(Pageable pageable) {
        return assignmentRequestSummaryService.findAll(pageable);
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

    private void sendEmail(String document, String identifier, String email) {
        String subject = String.format("Update for Assignment Request %s", identifier);
        gmailBuilderService.setTo(email)
                .setSubject(subject)
                .setBody(document)
                .send(this.gmailService);
    }

}
