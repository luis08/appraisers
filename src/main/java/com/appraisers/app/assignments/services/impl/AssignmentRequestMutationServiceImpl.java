package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.data.AssignmentRequestMutationRepository;
import com.appraisers.app.assignments.data.AssignmentRequestRepository;
import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestMutation;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import com.appraisers.app.assignments.services.AssignmentRequestDocumentService;
import com.appraisers.app.assignments.services.AssignmentRequestDocumentUploadService;
import com.appraisers.app.assignments.services.AssignmentRequestMutationService;
import com.appraisers.app.assignments.utils.AssignmentUtils;
import com.appraisers.app.gmail.GmailBuilderService;
import com.appraisers.app.gmail.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class AssignmentRequestMutationServiceImpl implements AssignmentRequestMutationService {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String NO_ATTACHMENT_CHANGES = "No attachment changes with this update";

    @Value("${com.appraisers.app.assignmentRequest.notificationEmail}")
    private String notificationEmail;

    @Autowired
    private AssignmentRequestMutationRepository assignmentRequestMutationRepository;

    @Autowired
    private AssignmentRequestRepository assignmentRequestRepository;

    @Autowired
    private AssignmentRequestDocumentService assignmentRequestDocumentService;

    @Autowired
    private AssignmentRequestDocumentUploadService assignmentRequestDocumentUploadService;

    @Autowired
    private GmailBuilderService gmailBuilderService;

    @Autowired
    private GmailService gmailService;

    @Override
    public AssignmentRequestMutation save(AssignmentRequestDto assignmentRequestDto, Long assignmentRequestId) throws ParseException {
        checkNotNull(assignmentRequestDto);
        checkNotNull(assignmentRequestId);
        AssignmentRequest assignmentRequest = assignmentRequestRepository.getOne(assignmentRequestId);
        AssignmentRequestMutation assignmentRequestMutation = new AssignmentRequestMutation();
        AssignmentUtils.populate(assignmentRequestDto, assignmentRequestMutation);
        assignmentRequestMutation.setAssignmentRequest(assignmentRequest);
        AssignmentRequestMutation savedAssignmentRequestMutation = assignmentRequestMutationRepository.save(assignmentRequestMutation);
        String document = assignmentRequestDocumentService.getDocument(savedAssignmentRequestMutation)
                .concat(LINE_SEPARATOR)
                .concat(NO_ATTACHMENT_CHANGES);
        String documentSavedMessage = assignmentRequestDocumentUploadService.getDocumentUploadedMessage(document, assignmentRequest, assignmentRequest.getIdentifier());
        document = document.concat(LINE_SEPARATOR)
                .concat(LINE_SEPARATOR)
                .concat(documentSavedMessage);
        sendEmail(document, assignmentRequest.getIdentifier());
        return savedAssignmentRequestMutation;
    }

    @Override
    public AssignmentRequestMutation get(Long id) {
        return assignmentRequestMutationRepository.getOne(id);
    }

    @Override
    public AssignmentRequestMutation getLatest(Long assignmentRequestId) {
        AssignmentRequest assignmentRequest = assignmentRequestRepository.getOne(assignmentRequestId);
        Optional<AssignmentRequestMutation> lastMutation = assignmentRequestMutationRepository.findFirstByAssignmentRequestOrderByDateCreatedDesc(assignmentRequest);
        if (lastMutation.isPresent()) {
            return lastMutation.get();
        } else {
            return getNewMutation(assignmentRequest);
        }
    }

    private void sendEmail(String document, String identifier) {
        String subject = String.format("Update for Assignment Request %s", identifier);
        gmailBuilderService.setTo(notificationEmail)
                .setSubject(subject)
                .setBody(document)
                .send(this.gmailService);
    }

    private AssignmentRequestMutation getNewMutation(AssignmentRequest assignmentRequest) {
        AssignmentRequestMutation assignmentRequestMutation = new AssignmentRequestMutation();
        AssignmentUtils.copy(assignmentRequest, assignmentRequestMutation);
        assignmentRequestMutation.setAssignmentRequest(assignmentRequest);

        return assignmentRequestMutationRepository.save(assignmentRequestMutation);
    }
}
