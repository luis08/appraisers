import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AssignmentRequest} from '../../../../assignment-request/src/app/assignment-request';
import {Observable} from 'rxjs';
import {AssignmentRequestBase} from '../AssignmentRequestBase';
import {HttpClient} from '@angular/common/http';
import {AssignmentRequestIdService} from '../assignment-request-id.service';

@Component({
  selector: 'app-assignment-request',
  templateUrl: './assignment-request.component.html',
  styleUrls: ['./assignment-request.component.css']
})
export class AssignmentRequestComponent extends AssignmentRequestBase implements OnInit {
  @Input() assignmentRequestId: number;
  identifier: string;
  registerForm: FormGroup;
  assignmentRequest: Observable<AssignmentRequest>;
  baseUrl = '/assignment';
  states: string[] = ['AL', 'AK', 'AZ', 'AR', 'CF', 'CL', 'CT', 'DL', 'DC', 'FL', 'GA', 'HA', 'ID', 'IL', 'IN',
    'IA', 'KA', 'KY', 'LA', 'ME', 'MD', 'MS', 'MC', 'MN', 'MI', 'MO', 'MT', 'NB', 'NV', 'NH', 'NJ', 'NM', 'NY',
    'NC', 'ND', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WN', 'WV', 'WS', 'WY'];

  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private assignmentRequestIdService: AssignmentRequestIdService) {
    super();
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      accountNumber: [''],
      updateEmail: [''],
      sendUpdateEmail: [false],
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

  getAssignmentRequestId(mutation): number {
    if (mutation && mutation.assignmentRequest && mutation.assignmentRequest.id) {
      return mutation.assignmentRequest.id;
    } else {
      return null;
    }
  }

  getIdentifier(mutation): string {
    return mutation && mutation.assignmentRequest && mutation.assignmentRequest.identifier
      ? mutation.assignmentRequest.identifier
      : null;
  }

  getAppraisal(): void {
    const url = this.baseUrl + '/latest/' + this.assignmentRequestId;
    this.http.get(url).toPromise()
      .then((mutation) => {
        const assignmentRequestId = this.getAssignmentRequestId(mutation);
        const assignmentRequest = Object.assign({
          id: assignmentRequestId
        }, mutation);
        this.registerForm.patchValue(assignmentRequest);
        this.assignmentRequestId = assignmentRequestId;
        this.identifier = this.getIdentifier(mutation);
      });
  }

  save(): void {
    this.setWaiting();
    let url = this.baseUrl + '/' + this.assignmentRequestId + '/update';
    const assignmentRequestMutation: AssignmentRequest = this.registerForm.value;
    const sendEmailValue: boolean = this.registerForm.get('sendUpdateEmail').value;
    console.log('Send Update:' + sendEmailValue);

    if (sendEmailValue) {
      url = url + '?sendUpdateEmail=true';
    }
    this.http.post(url, assignmentRequestMutation).toPromise()
      .then((mutation) => {
        this.setSuccess();
        this.assignmentRequestIdService.clear();
      }).catch((reason: any) => {
      this.setFailed();
    });
  }

  showSubmitButton(): boolean {
    return true;
    // return !this.isWaiting() && !this.hasSucceeded();
  }

  cancel() {
    this.setSuccess();
    this.assignmentRequestIdService.clear();
  }
}
