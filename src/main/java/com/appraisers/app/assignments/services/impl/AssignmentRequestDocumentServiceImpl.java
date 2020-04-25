package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.dto.utils.DtoUtils;
import com.appraisers.app.assignments.services.AssignmentRequestDocumentService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class AssignmentRequestDocumentServiceImpl implements AssignmentRequestDocumentService {
    private static final String NEW_LINE = System.lineSeparator();
    private static final String DIVIDER = " : ";
    private static final String HEADER_LINE = "================================================";

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public String getDocument(AssignmentRequest assignmentRequest) {
        checkNotNull(assignmentRequest);
        return getFullForm(assignmentRequest).toString();
    }

    private StringBuilder getFullForm(AssignmentRequest assignmentRequest) {
        ResourceBundle bundle = getBundle();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getLine(bundle, "identifier", assignmentRequest.getIdentifier()))
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append(getString(bundle, "companyHeader"))
                .append(NEW_LINE)
                .append(HEADER_LINE)
                .append(NEW_LINE)
                .append(getLine(bundle, "companyName", assignmentRequest.getCompanyName()))
                .append(NEW_LINE)
                .append(getLine(bundle, "companyAddress1", assignmentRequest.getCompanyAddress1()))
                .append(NEW_LINE)
                .append(getLine(bundle, "companyAddress2", assignmentRequest.getCompanyAddress2()))
                .append(NEW_LINE)
                .append(getLine(bundle, "companyCity", assignmentRequest.getCompanyCity()))
                .append(NEW_LINE)
                .append(getLine(bundle, "companyState", assignmentRequest.getCompanyState()))
                .append(NEW_LINE)
                .append(getLine(bundle, "companyZip", assignmentRequest.getCompanyZip()))
                .append(NEW_LINE)
                .append(getLine(bundle, "adjusterFirstName", assignmentRequest.getAdjusterFirstName()))
                .append(NEW_LINE)
                .append(getLine(bundle, "adjusterLastName", assignmentRequest.getAdjusterLastName()))
                .append(NEW_LINE)
                .append(getLine(bundle, "adjusterPhone", assignmentRequest.getAdjusterPhone()))
                .append(NEW_LINE)
                .append(getLine(bundle, "adjusterEmail", assignmentRequest.getAdjusterEmail()))
                .append(NEW_LINE)
                .append(getString(bundle, "insuredClaimantHeader"))
                .append(NEW_LINE)
                .append(HEADER_LINE)
                .append(NEW_LINE)
                .append(getLine(bundle, "claimNumber", assignmentRequest.getClaimNumber()))
                .append(NEW_LINE)
                .append(getLine(bundle, "insuredOrClaimant", assignmentRequest.getInsuredOrClaimant()))
                .append(NEW_LINE)
                .append(getLine(bundle, "policyNumber", assignmentRequest.getPolicyNumber()))
                .append(NEW_LINE)
                .append(getLine(bundle, "insuredClaimantSameAsOwner", DtoUtils.toStringOrNull(assignmentRequest.getInsuredClaimantSameAsOwner())))
                .append(NEW_LINE)
                .append(getLine(bundle, "deductibleAmount", assignmentRequest.getDeductibleAmount()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantFirst", assignmentRequest.getClaimantFirst()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantLast", assignmentRequest.getClaimantLast()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantFirst", assignmentRequest.getClaimantFirst()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantPhone", assignmentRequest.getClaimantPhone()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantEmail", assignmentRequest.getClaimantEmail()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantAddress1", assignmentRequest.getClaimantAddress1()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantAddress2", assignmentRequest.getClaimantAddress2()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantCity", assignmentRequest.getCompanyCity()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantState", assignmentRequest.getCompanyState()))
                .append(NEW_LINE)
                .append(getLine(bundle, "claimantZip", assignmentRequest.getCompanyZip()))
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append(getString(bundle, "vehicleInformationTitle"))
                .append(NEW_LINE)
                .append(HEADER_LINE)
                .append(NEW_LINE)
                .append(getLine(bundle, "make", assignmentRequest.getMake()))
                .append(NEW_LINE)
                .append(getLine(bundle, "model", assignmentRequest.getModel()))
                .append(NEW_LINE)
                .append(getLine(bundle, "year", assignmentRequest.getYear()))
                .append(NEW_LINE)
                .append(getLine(bundle, "vin", assignmentRequest.getVin()))
                .append(NEW_LINE)
                .append(getLine(bundle, "license", assignmentRequest.getLicense()))
                .append(NEW_LINE)
                .append(getLine(bundle, "licenseState", assignmentRequest.getLicenseState()))
                .append(NEW_LINE)
                .append(getLine(bundle, "dateOfLoss", getDate(assignmentRequest.getDateOfLoss())))
                .append(NEW_LINE)
                .append(getLine(bundle, "typeOfLoss", assignmentRequest.getTypeOfLoss()))
                .append(NEW_LINE)
                .append(getLine(bundle, "lossDescription", assignmentRequest.getLossDescription()))
                .append(NEW_LINE)
                .append(getLine(bundle, "providesCopyOfAppraisal", DtoUtils.toString(assignmentRequest.getProvidesCopyOfAppraisal())))
                .append(NEW_LINE)
                .append(getLine(bundle, "requestSalvageBids", DtoUtils.toStringOrNull(assignmentRequest.getRequestSalvageBids())))
                .append(NEW_LINE)
                .append(getLine(bundle, "provideAcvEvaluation", DtoUtils.toStringOrNull(assignmentRequest.getProvideAcvEvaluation())))
                .append(NEW_LINE)
                .append(getLine(bundle, "valuationMethod", assignmentRequest.getValuationMethod()))
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append(getString(bundle, "vehicleLocationTitle"))
                .append(NEW_LINE)
                .append(HEADER_LINE)
                .append(NEW_LINE)
                .append(getLine(bundle, "vehicleLocationName", assignmentRequest.getVehicleLocationName()))
                .append(NEW_LINE)
                .append(getLine(bundle, "isRepairFacility", DtoUtils.toStringOrNull(assignmentRequest.getIsRepairFacility())))
                .append(NEW_LINE)
                .append(getLine(bundle, "vehicleLocationName", assignmentRequest.getVehicleLocationName()))
                .append(NEW_LINE)
                .append(getLine(bundle, "vehicleLocationName", assignmentRequest.getVehicleLocationName()))
                .append(NEW_LINE)
                .append(getLine(bundle, "vehicleLocationAddress1", assignmentRequest.getVehicleLocationAddress1()))
                .append(NEW_LINE)
                .append(getLine(bundle, "vehicleLocationAddress2", assignmentRequest.getVehicleLocationAddress2()))
                .append(NEW_LINE)
                .append(getLine(bundle, "vehicleLocationCity", assignmentRequest.getVehicleLocationCity()))
                .append(NEW_LINE)
                .append(getLine(bundle, "vehicleLocationState", assignmentRequest.getVehicleLocationState()))
                .append(NEW_LINE)
                .append(getLine(bundle, "vehicleLocationZip", assignmentRequest.getVehicleLocationZip()))
                .append(NEW_LINE)
                .append(getLine(bundle, "vehicleLocationPhone", assignmentRequest.getVehicleLocationPhone()))
                .append(NEW_LINE);
        return stringBuilder;
    }


    private String getDate(Date date) {
        return date == null ? "" : dateFormatter.format(date);
    }

    private ResourceBundle getBundle() {
        ResourceBundle bundle =
                ResourceBundle.getBundle("AssignmentRequest", Locale.US);
        return bundle;
    }

    private String getLine(ResourceBundle resourceBundle, String keySuffix, String value) {
        return getString(resourceBundle, keySuffix) + DIVIDER + StringUtils.stripToEmpty(value);
    }

    private String getString(ResourceBundle resourceBundle, String suffix) {
        return resourceBundle.getString(getKey(suffix));
    }

    private String getKey(String suffix) {
        checkNotNull(suffix);
        return "assignmentRequest." + suffix;
    }
}
