import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AssignmentRequest} from '../../../../assignment-request/src/app/assignment-request';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-assignment-request',
  templateUrl: '../../../../common/templates/assignment-request.view.html',
  styleUrls: ['../../../../common/css/assignment-request.view.css']
})
export class AssignmentRequestComponent implements OnInit {

  form: FormGroup;
  user: Observable<AssignmentRequest>;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      accountNumber: [''],
      adjusterEmail: [''],
      adjusterFirstName: [''],
      adjusterLastName: [''],
      adjusterPhone: [''],
      claimantAddress1: [''],
      claimantAddress2: [''],
      claimantCity: [''],
      claimantEmail: [''],
      claimantFirst: [''],
      claimantLast: [''],
      claimantPhone: [''],
      claimantState: [''],
      claimantZip: [''],
      claimNumber: [''],
      companyAddress1: [''],
      companyAddress2: [''],
      companyName: [''],
      companyCity: [''],
      companyState: [''],
      companyZip: [''],
      dateOfLoss: [''],
      deductibleAmount: [''],
      insuredClaimantSameAsOwner: [''],
      insuredOrClaimant: [''],
      isRepairFacility: [''],
      license: [''],
      licenseState: [''],
      locationState: [''],
      lossDescription: [''],
      make: [''],
      model: [''],
      policyNumber: [''],
      provideAcvEvaluation: [''],
      providesCopyOfAppraisal: [''],
      requestSalvageBids: [''],
      typeOfLoss: [''],
      valuation: [''],
      valuationMethod: [''],
      vehicleLocationAddress1: [''],
      vehicleLocationAddress2: [''],
      vehicleLocationCity: [''],
      vehicleLocationName: [''],
      vehicleLocationPhone: [''],
      vehicleLocationState: [''],
      vehicleLocationZip: [''],
      vin: [''],
      year: ['']
    });

    this.user = this.userService.loadUser().pipe(
      tap(assignmentRequest => this.form.patchValue(assignmentRequest))
    );
  }

  submit() {
    if (this.form.valid) {
      console.log(this.form.value);
    }
  }

}
