package com.appraisers.app.assignments.utils;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestBase;
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

    public static void populate(AssignmentRequestDto dto, AssignmentRequestBase assignmentRequestBase) throws ParseException {
        assignmentRequestBase.setAccountNumber(dto.getAccountNumber());
        assignmentRequestBase.setAdjusterEmail(dto.getAdjusterEmail());
        assignmentRequestBase.setAdjusterFirstName(dto.getAdjusterFirstName());
        assignmentRequestBase.setAdjusterLastName(dto.getAdjusterLastName());
        assignmentRequestBase.setAdjusterPhone(dto.getAdjusterPhone());
        assignmentRequestBase.setClaimantAddress1(dto.getClaimantAddress1());
        assignmentRequestBase.setClaimantAddress2(dto.getClaimantAddress2());
        assignmentRequestBase.setClaimantCity(dto.getClaimantCity());
        assignmentRequestBase.setClaimantEmail(dto.getClaimantEmail());
        assignmentRequestBase.setClaimantFirst(dto.getClaimantFirst());
        assignmentRequestBase.setClaimantLast(dto.getClaimantLast());
        assignmentRequestBase.setClaimantPhone(dto.getClaimantPhone());
        assignmentRequestBase.setClaimantState(dto.getClaimantState());
        assignmentRequestBase.setClaimantZip(dto.getClaimantZip());
        assignmentRequestBase.setClaimNumber(dto.getClaimNumber());
        assignmentRequestBase.setCompanyAddress1(dto.getCompanyAddress1());
        assignmentRequestBase.setCompanyAddress2(dto.getCompanyAddress2());
        assignmentRequestBase.setCompanyCity(dto.getCompanyCity());
        assignmentRequestBase.setCompanyName(dto.getCompanyName());
        assignmentRequestBase.setCompanyState(dto.getCompanyState());
        assignmentRequestBase.setCompanyZip(dto.getCompanyZip());
        if (Strings.isNotEmpty(dto.getDateOfLoss())) {
            assignmentRequestBase.setDateOfLoss(new SimpleDateFormat("MM/dd/yyyy").parse(dto.getDateOfLoss()));
        }
        if (Strings.isNotEmpty(dto.getDeductibleAmount())) {
            assignmentRequestBase.setDeductibleAmount(dto.getDeductibleAmount());
        }

        assignmentRequestBase.setInsuredClaimantSameAsOwner(DtoUtils.parseOrNull(dto.getInsuredClaimantSameAsOwner()));
        assignmentRequestBase.setInsuredOrClaimant(dto.getInsuredOrClaimant());
        assignmentRequestBase.setIsRepairFacility(DtoUtils.parseOrNull(dto.getIsRepairFacility()));
        assignmentRequestBase.setLicense(dto.getLicense());
        assignmentRequestBase.setLicenseState(dto.getLicenseState());
        assignmentRequestBase.setLossDescription(dto.getLossDescription());
        assignmentRequestBase.setMake(dto.getMake());
        assignmentRequestBase.setModel(dto.getModel());
        assignmentRequestBase.setPolicyNumber(dto.getPolicyNumber());
        assignmentRequestBase.setProvideAcvEvaluation(DtoUtils.parseOrNull(dto.getProvideAcvEvaluation()));
        assignmentRequestBase.setProvidesCopyOfAppraisal(DtoUtils.parseOrNull(dto.getProvidesCopyOfAppraisal()));
        assignmentRequestBase.setRequestSalvageBids(DtoUtils.parseOrNull(dto.getRequestSalvageBids()));
        if (Objects.isNull(dto.getTypeOfLoss())) {
            assignmentRequestBase.setTypeOfLoss("Unknown");
        } else {
            assignmentRequestBase.setTypeOfLoss(dto.getTypeOfLoss());
        }
        assignmentRequestBase.setValuationMethod(dto.getValuationMethod());
        assignmentRequestBase.setVehicleLocationAddress1(dto.getVehicleLocationAddress1());
        assignmentRequestBase.setVehicleLocationAddress2(dto.getVehicleLocationAddress2());
        assignmentRequestBase.setVehicleLocationCity(dto.getVehicleLocationCity());
        assignmentRequestBase.setVehicleLocationName(dto.getVehicleLocationName());
        assignmentRequestBase.setVehicleLocationPhone(dto.getVehicleLocationPhone());
        assignmentRequestBase.setVehicleLocationState(dto.getVehicleLocationState());
        assignmentRequestBase.setVehicleLocationZip(dto.getVehicleLocationZip());
        assignmentRequestBase.setVin(dto.getVin());
        assignmentRequestBase.setYear(dto.getYear());
    }

    public static void copy(AssignmentRequestBase source, AssignmentRequestBase target) {
        target.setAccountNumber(source.getAccountNumber());
        target.setAdjusterEmail(source.getAdjusterEmail());
        target.setAdjusterFirstName(source.getAdjusterFirstName());
        target.setAdjusterLastName(source.getAdjusterLastName());
        target.setAdjusterPhone(source.getAdjusterPhone());
        target.setClaimantAddress1(source.getClaimantAddress1());
        target.setClaimantAddress2(source.getClaimantAddress2());
        target.setClaimantCity(source.getClaimantCity());
        target.setClaimantEmail(source.getClaimantEmail());
        target.setClaimantFirst(source.getClaimantFirst());
        target.setClaimantLast(source.getClaimantLast());
        target.setClaimantPhone(source.getClaimantPhone());
        target.setClaimantState(source.getClaimantState());
        target.setClaimantZip(source.getClaimantZip());
        target.setClaimNumber(source.getClaimNumber());
        target.setCompanyAddress1(source.getCompanyAddress1());
        target.setCompanyAddress2(source.getCompanyAddress2());
        target.setCompanyCity(source.getCompanyCity());
        target.setCompanyName(source.getCompanyName());
        target.setCompanyState(source.getCompanyState());
        target.setCompanyZip(source.getCompanyZip());
        target.setDateOfLoss(source.getDateOfLoss());

        if (Strings.isNotEmpty(source.getDeductibleAmount())) {
            target.setDeductibleAmount(source.getDeductibleAmount());
        }

        target.setInsuredClaimantSameAsOwner(source.getInsuredClaimantSameAsOwner());
        target.setInsuredOrClaimant(source.getInsuredOrClaimant());
        target.setIsRepairFacility(source.getIsRepairFacility());
        target.setLicense(source.getLicense());
        target.setLicenseState(source.getLicenseState());
        target.setLossDescription(source.getLossDescription());
        target.setMake(source.getMake());
        target.setModel(source.getModel());
        target.setPolicyNumber(source.getPolicyNumber());
        target.setProvideAcvEvaluation(source.getProvideAcvEvaluation());
        target.setProvidesCopyOfAppraisal(source.getProvidesCopyOfAppraisal());
        target.setRequestSalvageBids(source.getRequestSalvageBids());
        if (Objects.isNull(source.getTypeOfLoss())) {
            target.setTypeOfLoss("Unknown");
        } else {
            target.setTypeOfLoss(source.getTypeOfLoss());
        }
        target.setValuationMethod(source.getValuationMethod());
        target.setVehicleLocationAddress1(source.getVehicleLocationAddress1());
        target.setVehicleLocationAddress2(source.getVehicleLocationAddress2());
        target.setVehicleLocationCity(source.getVehicleLocationCity());
        target.setVehicleLocationName(source.getVehicleLocationName());
        target.setVehicleLocationPhone(source.getVehicleLocationPhone());
        target.setVehicleLocationState(source.getVehicleLocationState());
        target.setVehicleLocationZip(source.getVehicleLocationZip());
        target.setVin(source.getVin());
        target.setYear(source.getYear());
    }

    public static ZonedDateTime getZonedDateTime() {
        ZoneId zoneId = ZoneId.of(US_EASTERN);
        LocalDateTime localDate = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, zoneId);
        return zonedDateTime;
    }
}
