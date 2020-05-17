package com.appraisers.app.assignments.dto;

import com.appraisers.app.assignments.domain.AssignmentRequestMutation;
import com.appraisers.app.assignments.domain.dto.DomainComponentProjection;
import com.appraisers.app.assignments.dto.utils.DtoUtils;

public class AssignmentRequestMutationProjection extends DomainComponentProjection {
    private final AssignmentRequestMutation component;

    public AssignmentRequestMutationProjection(AssignmentRequestMutation assignmentRequestMutation) {
        super(assignmentRequestMutation.getId(), assignmentRequestMutation.isActive());
        this.component = assignmentRequestMutation;
    }


    public Long getId() {
        return this.component.getId();
    }

    public String getIdentifier() {
        return this.component.getAssignmentRequest().getIdentifier();
    }

    public String getAccountNumber() {
        return this.component.getAccountNumber();
    }

    public String getAdjusterEmail() {
        return this.component.getAdjusterEmail();
    }

    public String getAdjusterFirstName() {
        return this.component.getAdjusterFirstName();
    }

    public String getAdjusterLastName() {
        return this.component.getAdjusterLastName();
    }

    public String getAdjusterPhone() {
        return this.component.getAdjusterPhone();
    }

    public String getClaimantAddress1() {
        return this.component.getClaimantAddress1();
    }

    public String getClaimantAddress2() {
        return this.component.getClaimantAddress2();
    }

    public String getClaimantCity() {
        return this.component.getClaimantCity();
    }

    public String getClaimantEmail() {
        return this.component.getClaimantEmail();
    }

    public String getClaimantFirst() {
        return this.component.getClaimantFirst();
    }

    public String getClaimantLast() {
        return this.component.getClaimantLast();
    }

    public String getClaimantPhone() {
        return this.component.getClaimantPhone();
    }

    public String getClaimantState() {
        return this.component.getClaimantState();
    }

    public String getClaimantZip() {
        return this.component.getClaimantZip();
    }

    public String getClaimNumber() {
        return this.component.getClaimNumber();
    }

    public String getCompanyAddress1() {
        return this.component.getCompanyAddress1();
    }

    public String getCompanyAddress2() {
        return this.component.getCompanyAddress2();
    }

    public String getCompanyCity() {
        return this.component.getCompanyCity();
    }

    public String getCompanyName() {
        return this.component.getCompanyName();
    }

    public String getCompanyState() {
        return this.component.getCompanyState();
    }

    public String getCompanyZip() {
        return this.component.getCompanyZip();
    }

    public String getDateOfLoss() {
        return DtoUtils.toStringOrNull(this.component.getDateOfLoss());
    }

    public String getDeductibleAmount() {
        return this.component.getDeductibleAmount();
    }

    public String getInsuredClaimantSameAsOwner() {
        return DtoUtils.toStringOrNull(this.component.getInsuredClaimantSameAsOwner());
    }

    public String getInsuredOrClaimant() {
        return this.component.getInsuredOrClaimant();
    }

    public String getIsRepairFacility() {
        return DtoUtils.toStringOrNull(this.component.getIsRepairFacility());
    }

    public String getLicense() {
        return this.component.getLicense();
    }

    public String getLicenseState() {
        return this.component.getLicenseState();
    }

    public String getLossDescription() {
        return this.component.getLossDescription();
    }

    public String getMake() {
        return this.component.getMake();
    }

    public String getModel() {
        return this.component.getModel();
    }

    public String getPolicyNumber() {
        return this.component.getPolicyNumber();
    }

    public String getProvideAcvEvaluation() {
        return DtoUtils.toStringOrNull(this.component.getProvideAcvEvaluation());
    }

    public String getProvidesCopyOfAppraisal() {
        return DtoUtils.toStringOrNull(this.component.getProvidesCopyOfAppraisal());
    }

    public String getRequestSalvageBids() {
        return DtoUtils.toStringOrNull(this.component.getRequestSalvageBids());
    }

    public String getTypeOfLoss() {
        return this.component.getTypeOfLoss();
    }

    public String getValuationMethod() {
        return this.component.getValuationMethod();
    }

    public String getVehicleLocationAddress1() {
        return this.component.getVehicleLocationAddress1();
    }

    public String getVehicleLocationAddress2() {
        return this.component.getVehicleLocationAddress2();
    }

    public String getVehicleLocationCity() {
        return this.component.getVehicleLocationCity();
    }

    public String getVehicleLocationName() {
        return this.component.getVehicleLocationName();
    }

    public String getVehicleLocationPhone() {
        return this.component.getVehicleLocationPhone();
    }

    public String getVehicleLocationState() {
        return this.component.getVehicleLocationState();
    }

    public String getVehicleLocationZip() {
        return this.component.getVehicleLocationZip();
    }

    public String getVin() {
        return this.component.getVin();
    }

    public String getYear() {
        return this.component.getYear();
    }

    public AssignmentRequestProjection getAssignmentRequest() {
        return new AssignmentRequestProjection(component.getAssignmentRequest());
    }
}
