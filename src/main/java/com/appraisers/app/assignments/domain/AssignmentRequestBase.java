package com.appraisers.app.assignments.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class AssignmentRequestBase extends DomainComponent {

    @Getter
    @Setter
    @Column(name = "company_name")
    private String companyName;

    @Getter
    @Setter
    @Column(name = "account_number")
    private String accountNumber;

    @Getter
    @Setter
    @Column(name = "adjuster_first")
    private String adjusterFirstName;

    @Getter
    @Setter
    @Column(name = "adjuster_last")
    private String adjusterLastName;

    @Getter
    @Setter
    @Column(name = "adjuster_phone")
    private String adjusterPhone;

    @Getter
    @Setter
    @Column(name = "adjuster_email")
    private String adjusterEmail;

    @Getter
    @Setter
    @Column(name = "company_address1")
    private String companyAddress1;

    @Getter
    @Setter
    @Column(name = "company_address2")
    private String companyAddress2;

    @Getter
    @Setter
    @Column(name = "company_city")
    private String companyCity;

    @Getter
    @Setter
    @Column(name = "company_state")
    private String companyState;

    @Getter
    @Setter
    @Column(name = "company_zip")
    private String companyZip;

    @Getter
    @Setter
    @Column(name = "claim_number")
    private String claimNumber;

    @Getter
    @Setter
    @Column(name = "insured_or_claimant")
    private String insuredOrClaimant;

    @Getter
    @Setter
    @Column(name = "policy_number")
    private String policyNumber;

    @Getter
    @Setter
    @Column(name = "same_as_owner")
    private Boolean insuredClaimantSameAsOwner;

    @Getter
    @Setter
    @Column(name = "deductible_amount")
    private String deductibleAmount;

    @Getter
    @Setter
    @Column(name = "claimant_first")
    private String claimantFirst;

    @Getter
    @Setter
    @Column(name = "claimant_last")
    private String claimantLast;

    @Getter
    @Setter
    @Column(name = "claimant_phone")
    private String claimantPhone;

    @Getter
    @Setter
    @Column(name = "claimant_email")
    private String claimantEmail;

    @Getter
    @Setter
    @Column(name = "claimant_address1")
    private String claimantAddress1;

    @Getter
    @Setter
    @Column(name = "claimant_address2")
    private String claimantAddress2;

    @Getter
    @Setter
    @Column(name = "claimant_city")
    private String claimantCity;

    @Getter
    @Setter
    @Column(name = "claimant_state")
    private String claimantState;

    @Getter
    @Setter
    @Column(name = "claimant_zip")
    private String claimantZip;

    @Getter
    @Setter
    @Column(name = "vehicle_make")
    private String make;

    @Getter
    @Setter
    @Column(name = "vehicle_model")
    private String model;

    @Getter
    @Setter
    @Column(name = "vehicle_year")
    private String year;

    @Getter
    @Setter
    @Column(name = "vehicle_vin")
    private String vin;

    @Getter
    @Setter
    @Column(name = "vehicle_license")
    private String license;

    @Getter
    @Setter
    @Column(name = "vehicle_state")
    private String licenseState;

    @Getter
    @Setter
    @Column(name = "loss_date")
    private Date dateOfLoss;

    @Getter
    @Setter
    @Column(name = "type")
    private String typeOfLoss;

    @Getter
    @Setter
    @Column(name = "loss_description")
    private String lossDescription;

    @Getter
    @Setter
    @Column(name = "has_copy_of_appraisal")
    private Boolean providesCopyOfAppraisal;

    @Getter
    @Setter
    @Column(name = "has_salvage_bids")
    private Boolean requestSalvageBids;

    @Getter
    @Setter
    @Column(name = "has_acv")
    private Boolean provideAcvEvaluation;

    @Getter
    @Setter
    @Column(name = "acv_evaluation_method")
    private String valuationMethod;

    @Getter
    @Setter
    @Column(name = "vehicle_location_name")
    private String vehicleLocationName;

    @Getter
    @Setter
    @Column(name = "is_vehicle_location_repair_facility")
    private Boolean isRepairFacility;

    @Getter
    @Setter
    @Column(name = "vehicle_location_address1")
    private String vehicleLocationAddress1;

    @Getter
    @Setter
    @Column(name = "vehicle_location_address2")
    private String vehicleLocationAddress2;

    @Getter
    @Setter
    @Column(name = "vehicle_location_city")
    private String vehicleLocationCity;

    @Getter
    @Setter
    @Column(name = "vehicle_location_state")
    private String vehicleLocationState;

    @Getter
    @Setter
    @Column(name = "vehicle_location_zip")
    private String vehicleLocationZip;

    @Getter
    @Setter
    @Column(name = "vehicle_location_phone")
    private String vehicleLocationPhone;

}
