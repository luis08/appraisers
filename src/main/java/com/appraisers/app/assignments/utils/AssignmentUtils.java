package com.appraisers.app.assignments.utils;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.dto.AssignmentRequestDto;
import com.appraisers.app.assignments.dto.utils.DtoUtils;
import org.apache.logging.log4j.util.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class AssignmentUtils {

    public static final String US_EASTERN = "US/Eastern";

    public static AssignmentRequest getAssignmentRequest(AssignmentRequestDto dto) throws ParseException {
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
        if (Strings.isNotEmpty(dto.getDateOfLoss())) {
            assignmentRequest.setDateOfLoss(new SimpleDateFormat("MM/dd/yyyy").parse(dto.getDateOfLoss()));
        }
        if (Strings.isNotEmpty(dto.getDeductibleAmount())) {
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
        if (Objects.isNull(dto.getTypeOfLoss())) {
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

    public static ZonedDateTime getZonedDateTime() {
        ZoneId zoneId = ZoneId.of(US_EASTERN);
        LocalDateTime localDate = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, zoneId);
        return zonedDateTime;
    }
}
