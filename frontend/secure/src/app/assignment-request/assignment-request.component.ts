import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AssignmentRequest} from '../../../../assignment-request/src/app/assignment-request';
import {Observable} from 'rxjs';
import {AssignmentRequestBase} from '../AssignmentRequestBase';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-assignment-request',
  templateUrl: './assignment-request.component.html',
  styleUrls: ['./assignment-request.component.css']
})
export class AssignmentRequestComponent extends AssignmentRequestBase implements OnInit {
  @Input() assignmentRequestId: number;
  registerForm: FormGroup;
  assignmentRequest: Observable<AssignmentRequest>;
  baseUrl = '/assignment/latest';
  states: string[] = ['AL', 'AK', 'AZ', 'AR', 'CF', 'CL', 'CT', 'DL', 'DC', 'FL', 'GA', 'HA', 'ID', 'IL', 'IN',
    'IA', 'KA', 'KY', 'LA', 'ME', 'MD', 'MS', 'MC', 'MN', 'MI', 'MO', 'MT', 'NB', 'NV', 'NH', 'NJ', 'NM', 'NY',
    'NC', 'ND', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WN', 'WV', 'WS', 'WY'];

  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient) {
    super();
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
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
    this.getAppraisal();
  }
  getAssignmentRequestId(mutation) : number {
    if (mutation && mutation.assignmentRequest && mutation.assignmentRequest.id) {
      console.log('Found assignment request id: ' + mutation.assignmentRequest.id);
      return mutation.assignmentRequest.id;
    } else {
      console.log('Found no assignment request id: ');
      return null;
    }
  }

  getAppraisal(): void {
    this.http.get(this.baseUrl + '/' + this.assignmentRequestId).toPromise()
      .then((mutation) => {
        const assignmentRequestId = this.getAssignmentRequestId(mutation);
        const assignmentRequest = Object.assign({
          id: assignmentRequestId
        }, mutation);
        this.registerForm.patchValue(assignmentRequest);
      });
  }

  submit() {

  }

  save(): void {
    const url = this.baseUrl + '/' + this.getAssignmentRequestId(this.assignmentRequest.);
    const assignmentRequestMutation = {};
    this.http.post(url, assignmentRequestMutation).toPromise()
      .then((mutation) => {
      })
      .catch((reason: any) => {
      });
  }

  hasFailed(): boolean {
    return false;
  }

  showSubmitButton(): boolean {
    return true;
  }
}
