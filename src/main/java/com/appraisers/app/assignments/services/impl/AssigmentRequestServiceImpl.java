package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.data.AssignmentRequestRepository;
import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import com.appraisers.app.assignments.services.AssignmentRequestAttachmentService;
import com.appraisers.app.assignments.services.AssignmentRequestService;
import com.appraisers.app.assignments.utils.AssignmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class AssigmentRequestServiceImpl implements AssignmentRequestService {
    @Autowired
    private AssignmentRequestAttachmentService assignmentRequestAttachmentService;

    @Autowired
    private AssignmentRequestRepository assignmentRequestRepository;
    public static final String IDENTIFIER_MASK = "%s%02d%02d-%03d";

    @Override
    public AssignmentRequest create(AssignmentRequestDto dto) throws ParseException {
        checkNotNull(dto);
        AssignmentRequest assignmentRequest = AssignmentUtils.getAssignmentRequest(dto);
        assignmentRequest.setActive(true);
        assignmentRequest.setIdentifier(getIdentifier());

        assignmentRequestRepository.save(assignmentRequest);
        assignmentRequest.setAttachments(createAttachments(dto, assignmentRequest));
        return assignmentRequestRepository.getOne(assignmentRequest.getId());
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

    private Set<AssignmentRequestAttachment> createAttachments(AssignmentRequestDto dto, AssignmentRequest assignmentRequest) {
        if (Objects.nonNull(dto.getUploadingFiles())) {
            List<MultipartFile> multipartFiles = Arrays.asList(dto.getUploadingFiles());
            return assignmentRequestAttachmentService.create(assignmentRequest, multipartFiles).stream().collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }

    private String getIdentifier() {
        LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        java.util.Date from = Date.from(start.atStartOfDay(ZoneId.of(AssignmentUtils.US_EASTERN)).toInstant());
        java.util.Date to = Date.from(end.plus(Period.ofDays(1)).atStartOfDay(ZoneId.of(AssignmentUtils.US_EASTERN)).toInstant());
        //int sequence = assignmentRequestRepository.countAllByDateCreatedGreaterThanEqualAndDateCreatedLessThan(from, to);
        int sequence = assignmentRequestRepository.findAllWithDateCreatedBetween(from, to).size();
        int year = start.getYear();
        String theYear = Integer.toString(year).substring(2);
        return String.format(IDENTIFIER_MASK, theYear, start.getMonth().getValue(), LocalDate.now().getDayOfMonth(), sequence);
    }
}

