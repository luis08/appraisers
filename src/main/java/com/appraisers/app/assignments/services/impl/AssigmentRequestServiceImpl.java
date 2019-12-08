package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.data.AssignmentRequestRepository;
import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import com.appraisers.app.assignments.dto.utils.DtoUtils;
import com.appraisers.app.assignments.services.AssignmentRequestAttachmentService;
import com.appraisers.app.assignments.services.AssignmentRequestService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class AssigmentRequestServiceImpl implements AssignmentRequestService {
    @Autowired
    private AssignmentRequestAttachmentService assignmentRequestAttachmentService;

    @Autowired
    private AssignmentRequestRepository assignmentRequestRepository;

    @Override
    public AssignmentRequest save(AssignmentRequestDto dto) throws ParseException {
        checkNotNull(dto);
        AssignmentRequest assignmentRequest = getAssignmentRequest(dto);
        assignmentRequest.setActive(true);
        AssignmentRequest saved = assignmentRequestRepository.save(assignmentRequest);
        List<AssignmentRequestAttachment> attachments = getAssignmentRequestAttachments(dto, saved);
        assignmentRequest.setAttachments(attachments.stream().collect(Collectors.toSet()));
        return assignmentRequestRepository.saveAndFlush(saved);
    }

    private List<AssignmentRequestAttachment> getAssignmentRequestAttachments(AssignmentRequestDto dto, AssignmentRequest assignmentRequest) {
        if (Objects.nonNull(dto.getUploadingFiles())) {
            List<MultipartFile> multipartFiles = Arrays.asList(dto.getUploadingFiles());
            return assignmentRequestAttachmentService.create(assignmentRequest, multipartFiles);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public AssignmentRequest get(Long id) {
        checkNotNull(id);
        return assignmentRequestRepository.getOne(id);
    }

    private AssignmentRequest getAssignmentRequest(AssignmentRequestDto dto) throws ParseException {
        AssignmentRequest assignmentRequest = new AssignmentRequest();
        assignmentRequest.setAccountNumber(dto.getAccountNumber());
        assignmentRequest.setAdjusterEmail(dto.getAdjusterEmail());
        assignmentRequest.setAdjusterFirstName(dto.getAdjusterFirstName());
        assignmentRequest.setAdjusterLastName(dto.getAdjusterLastName());
        assignmentRequest.setAdjusterPhone(dto.getAdjusterPhone());
        assignmentRequest.setClaimantAddress1(dto.getClaimantAddress1());
        assignmentRequest.setClaimantAddress2(dto.getClaimantAddress2());
        assignmentRequest.setClaimantCity(dto.getClaimantCity());
        assignmentRequest.setClaimantEmail(dto.getClaimantEmail());
        assignmentRequest.setClaimantFirst(dto.getClaimantFirst());
        assignmentRequest.setClaimantLast(dto.getClaimantLast());
        assignmentRequest.setClaimantPhone(dto.getClaimantPhone());
        assignmentRequest.setClaimantState(dto.getClaimantState());
        assignmentRequest.setClaimantZip(dto.getClaimantZip());
        assignmentRequest.setClaimNumber(dto.getClaimNumber());
        assignmentRequest.setCompanyAddress1(dto.getCompanyAddress1());
        assignmentRequest.setCompanyAddress2(dto.getCompanyAddress2());
        assignmentRequest.setCompanyCity(dto.getCompanyCity());
        assignmentRequest.setCompanyName(dto.getCompanyName());
        assignmentRequest.setCompanyState(dto.getCompanyState());
        assignmentRequest.setCompanyZip(dto.getCompanyZip());
        if(Strings.isNotEmpty(dto.getDateOfLoss())) {
            assignmentRequest.setDateOfLoss(new SimpleDateFormat("MM/dd/yyyy").parse(dto.getDateOfLoss()));
        }
        if(Strings.isNotEmpty(dto.getDeductibleAmount())){
            assignmentRequest.setDeductibleAmount(dto.getDeductibleAmount());
        }

        assignmentRequest.setInsuredClaimantSameAsOwner(DtoUtils.parseOrNull(dto.getInsuredClaimantSameAsOwner()));
        assignmentRequest.setInsuredOrClaimant(dto.getInsuredOrClaimant());
        assignmentRequest.setIsRepairFacility(DtoUtils.parseOrNull(dto.getIsRepairFacility()));
        assignmentRequest.setLicense(dto.getLicense());
        assignmentRequest.setLicenseState(dto.getLicenseState());
        assignmentRequest.setLossDescription(dto.getLossDescription());
        assignmentRequest.setMake(dto.getMake());
        assignmentRequest.setModel(dto.getModel());
        assignmentRequest.setPolicyNumber(dto.getPolicyNumber());
        assignmentRequest.setProvideAcvEvaluation(DtoUtils.parseOrNull(dto.getProvideAcvEvaluation()));
        assignmentRequest.setProvidesCopyOfAppraisal(DtoUtils.parseOrNull(dto.getProvidesCopyOfAppraisal()));
        assignmentRequest.setRequestSalvageBids(DtoUtils.parseOrNull(dto.getRequestSalvageBids()));
        if(Objects.isNull(dto.getTypeOfLoss())) {
            assignmentRequest.setTypeOfLoss("Unknown");
        } else {
            assignmentRequest.setTypeOfLoss(dto.getTypeOfLoss());
        }
        assignmentRequest.setValuationMethod(dto.getValuationMethod());
        assignmentRequest.setVehicleLocationAddress1(dto.getVehicleLocationAddress1());
        assignmentRequest.setVehicleLocationAddress2(dto.getVehicleLocationAddress2());
        assignmentRequest.setVehicleLocationCity(dto.getVehicleLocationCity());
        assignmentRequest.setVehicleLocationName(dto.getVehicleLocationName());
        assignmentRequest.setVehicleLocationPhone(dto.getVehicleLocationPhone());
        assignmentRequest.setVehicleLocationState(dto.getVehicleLocationState());
        assignmentRequest.setVehicleLocationZip(dto.getVehicleLocationZip());
        assignmentRequest.setVin(dto.getVin());
        assignmentRequest.setYear(dto.getYear());
        return assignmentRequest;
    }
}
