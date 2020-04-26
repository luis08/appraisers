package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.data.AssignmentRequestRepository;
import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import com.appraisers.app.assignments.dto.AssignmentRequestAttachmentSave;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import com.appraisers.app.assignments.dto.GoogleUploadItemDto;
import com.appraisers.app.assignments.services.AssignmentRequestAttachmentService;
import com.appraisers.app.assignments.services.AssignmentRequestDocumentService;
import com.appraisers.app.assignments.services.AssignmentRequestService;
import com.appraisers.app.assignments.utils.AssignmentUtils;
import com.appraisers.app.gbuckets.GDrive;
import com.appraisers.app.gbuckets.GDriveCommonResponse;
import com.appraisers.app.gmail.GmailBuilderService;
import com.appraisers.app.gmail.GmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Date.from;

@Service
public class AssigmentRequestServiceImpl implements AssignmentRequestService {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String INVALID_RESPONSE = "Unable to upload text document with the entire Assignment Request";

    @Value("${com.appraisers.app.assignmentRequest.notificationEmail}")
    private String notificationEmail;

    @Autowired
    private AssignmentRequestAttachmentService assignmentRequestAttachmentService;

    @Autowired
    private GmailService gmailService;

    @Autowired
    private AssignmentRequestRepository assignmentRequestRepository;

    @Autowired
    private AssignmentRequestDocumentService assignmentRequestDocumentService;

    @Autowired
    private GDrive googleDrive;

    @Autowired
    private GmailBuilderService gmailBuilderService;

    public static final String IDENTIFIER_MASK = "%s%02d%02d-%03d";

    @Override
    public AssignmentRequest create(AssignmentRequestDto dto) throws Exception {
        checkNotNull(dto);
        AssignmentRequest assignmentRequest = AssignmentUtils.getAssignmentRequest(dto);
        assignmentRequest.setActive(true);
        assignmentRequest.setIdentifier(getIdentifier());

        assignmentRequestRepository.save(assignmentRequest);
        Set<AssignmentRequestAttachmentSave> attachments = createAttachments(dto, assignmentRequest);
        Set<AssignmentRequestAttachment> validAttachments = attachments.stream().map(AssignmentRequestAttachmentSave::getAssignmentRequestAttachment).filter(Objects::nonNull).collect(Collectors.toSet());
        assignmentRequest.setAttachments(validAttachments);

        AssignmentRequest finalRequest = assignmentRequestRepository.getOne(assignmentRequest.getId());

        String document = assignmentRequestDocumentService.getDocument(finalRequest)
                .concat(LINE_SEPARATOR)
                .concat(attachments.stream()
                        .map(AssignmentRequestAttachmentSave::getSaveMessage)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(LINE_SEPARATOR)));
        String documentSavedMessage = getDocumentUploadedMessage(document, assignmentRequest);
        document = document.concat(LINE_SEPARATOR)
                .concat(LINE_SEPARATOR)
                .concat(documentSavedMessage);
        sendEmail(document, assignmentRequest.getIdentifier());

        return finalRequest;
    }

    private String getDocumentUploadedMessage(String document, AssignmentRequest assignmentRequest) {
        String fileName = assignmentRequest.getIdentifier().concat(".txt");
        try {
            GoogleUploadItemDto googleUploadItemDto = new GoogleUploadItemDto("text/plain", fileName, fileName, assignmentRequest.getIdentifier(), document.getBytes());
            GDriveCommonResponse gDriveCommonResponse = googleDrive.uploadFile(googleUploadItemDto);
            if (Objects.isNull(gDriveCommonResponse)) {
                return INVALID_RESPONSE;
            } else {
                return Optional.ofNullable(gDriveCommonResponse.getId()).orElse(INVALID_RESPONSE);
            }
        } catch (JsonProcessingException e) {
            return INVALID_RESPONSE;
        }
    }

    private void sendEmail(String document, String identifier) {
        String subject = String.format("Assignment Request Received %s", identifier);
        gmailBuilderService.setTo(notificationEmail)
                .setSubject(subject)
                .setBody(document)
                .send(this.gmailService);
    }

    @Override
    public Page<AssignmentRequest> findAll(Pageable pageable) {
        checkNotNull(pageable, "You must request a page");
        return assignmentRequestRepository.findAll(pageable);
    }

    @Override
    public AssignmentRequest get(Long id) {
        checkNotNull(id);
        return assignmentRequestRepository.getOne(id);
    }

    private Set<AssignmentRequestAttachmentSave> createAttachments(AssignmentRequestDto dto, AssignmentRequest assignmentRequest) throws Exception {
        if (Objects.nonNull(dto.getUploadingFiles())) {
            List<MultipartFile> multipartFiles = Arrays.asList(dto.getUploadingFiles());
            return assignmentRequestAttachmentService.create(assignmentRequest, multipartFiles)
                    .stream()
                    .collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }

    private String getIdentifier() {
        LocalDate today = LocalDate.now();
        Date from = from(today.atStartOfDay(ZoneId.of(AssignmentUtils.US_EASTERN)).toInstant());
        Date to = from(today.plus(Period.ofDays(1)).atStartOfDay(ZoneId.of(AssignmentUtils.US_EASTERN)).toInstant());
        int sequence = assignmentRequestRepository.findAllWithDateCreatedBetween(from, to).size();
        int year = today.getYear();
        String theYear = Integer.toString(year).substring(2);
        return String.format(IDENTIFIER_MASK, theYear, today.getMonth().getValue(), LocalDate.now().getDayOfMonth(), sequence);
    }
}

