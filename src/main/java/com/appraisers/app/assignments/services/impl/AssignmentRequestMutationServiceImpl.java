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
import java.util.*;
import java.util.stream.Collectors;

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
        assignmentRequestMutation.setUpdateEmail(assignmentRequestDto.getUpdateEmail());
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
    public AssignmentRequestMutation get(Long assignmentRequestMutationId) {
        checkNotNull(assignmentRequestMutationId);
        return assignmentRequestMutationRepository.getOne(assignmentRequestMutationId);
    }

    @Override
    public List<AssignmentRequestMutation> get(List<AssignmentRequest> assignmentRequests) {
        checkNotNull(assignmentRequests);
        List<AssignmentRequestMutation> allMutations = assignmentRequestMutationRepository.findByAssignmentRequestIn(assignmentRequests);
        Map<Long, List<AssignmentRequestMutation>> groups = allMutations.stream().collect(Collectors.groupingBy(m -> m.getAssignmentRequest().getId()));
        List<AssignmentRequestMutation> mutations = new ArrayList<>();

        for (Map.Entry<Long, List<AssignmentRequestMutation>> group : groups.entrySet()) {
            List<AssignmentRequestMutation> items = group.getValue();
            if (!items.isEmpty()) {
                Optional<AssignmentRequestMutation> max = items.stream().max(Comparator.comparing(AssignmentRequestMutation::getDateCreated));
                if (max.isPresent()) {
                    mutations.add(max.get());
                }
            }
        }
        ;
        return mutations;
    }

    @Override
    public AssignmentRequestMutation getLatest(Long assignmentRequestId) {
        checkNotNull(assignmentRequestId);
        AssignmentRequest assignmentRequest = assignmentRequestRepository.getOne(assignmentRequestId);
        Optional<AssignmentRequestMutation> lastMutation = assignmentRequestMutationRepository.findFirstByAssignmentRequestOrderByDateCreatedDesc(assignmentRequest);
        if (lastMutation.isPresent()) {
            return lastMutation.get();
        } else {
            return getNewMutation(assignmentRequest);
        }
    }

    @Override
    public List<AssignmentRequestMutation> getHistory(AssignmentRequest assignmentRequest) {
        checkNotNull(assignmentRequest);
        return assignmentRequestMutationRepository.findByAssignmentRequest(assignmentRequest);
    }

    @Override
    public List<AssignmentRequestMutation> getAllHistory(List<AssignmentRequest> assignmentRequests) {
        checkNotNull(assignmentRequests);
        return assignmentRequestMutationRepository.findByAssignmentRequestIn(assignmentRequests);
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
