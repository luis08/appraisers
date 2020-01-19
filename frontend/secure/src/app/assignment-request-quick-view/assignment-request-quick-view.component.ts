import {Component, Input, OnInit} from '@angular/core';
import {AssignmentRequest} from "../../../../assignment-request/src/app/assignment-request";
import {HttpClient, HttpHeaders} from '@angular/common/http';

const parsedUrl = new URL(window.location.href);
const assignmentUrl = parsedUrl.origin.concat("/assignment");
const attachmentsUrl = parsedUrl.origin.concat("/attachment");

@Component({
  selector: 'app-assignment-request-quick-view',
  templateUrl: './assignment-request-quick-view.component.html',
  styleUrls: ['./assignment-request-quick-view.component.css']
})
export class AssignmentRequestQuickViewComponent implements OnInit {
  @Input() assignmentRequest: AssignmentRequest;
  fields: Array<any>;
  attachments: Array<any>;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this._loadAttachments();
    this.fields = new Array<any>();
    let allFields = fieldList.sort(f => f.location).map(f => {
      return {
        key: f.key,
        value: this.assignmentRequest[f.key],
        label: f.label
      };
    });

    let i = 0;
    while (i < allFields.length) {
      let includeSecond = (i + 1) < allFields.length;
      this.fields.push({
        field1: allFields[i].key,
        value1: allFields[i].value,
        label1: allFields[i].label,
        field2: includeSecond ? allFields[i + 1].key : null,
        value2: includeSecond ? allFields[i + 1].value : null,
        label2: includeSecond ? allFields[i + 1].label : null,
      });
      i = i + 2;
    }
  }

  close() {

  }

  _loadAttachments() {
    let finalUrl = assignmentUrl.concat('/', this.assignmentRequest.id.toString());
    this.http.get(finalUrl).toPromise().then((ar: any) => {
      ar.attachments.forEach(a => a.href = this.getUrl(a));
      this.attachments = ar.attachments;
    });
  }

  getUrl(attachment: any) {
    return attachmentsUrl.concat('/', attachment.id.toString());
  }
}

const fieldList = [{'key': 'companyName', 'label': 'Company Name', 'location': 0},
  {'key': 'adjusterFirstName', 'label': 'Adjuster First Name', 'location': 2},
  {'key': 'adjusterLastName', 'label': 'Adjuster Last Name', 'location': 3},
  {'key': 'adjusterPhone', 'label': 'Adjuster Phone', 'location': 4},
  {'key': 'adjusterEmail', 'label': 'Adjuster Email', 'location': 5},
  {'key': 'companyAddress1', 'label': 'Company Address1', 'location': 6},
  {'key': 'companyAddress2', 'label': 'Company Address2', 'location': 7},
  {'key': 'companyCity', 'label': 'Company City', 'location': 8},
  {'key': 'companyState', 'label': 'Company State', 'location': 9},
  {'key': 'companyZip', 'label': 'Company Zip', 'location': 10},
  {'key': 'claimNumber', 'label': 'Claim Number', 'location': 11},
  {'key': 'insuredOrClaimant', 'label': 'Insured Or Claimant', 'location': 12},
  {'key': 'policyNumber', 'label': 'Policy Number', 'location': 13},
  {'key': 'insuredClaimantSameAsOwner', 'label': 'Insured Same As Owner', 'location': 14},
  {'key': 'deductibleAmount', 'label': 'Deductible Amount', 'location': 15},
  {'key': 'claimantFirst', 'label': 'Claimant First', 'location': 16},
  {'key': 'claimantLast', 'label': 'Claimant Last', 'location': 17},
  {'key': 'claimantPhone', 'label': 'Claimant Phone', 'location': 18},
  {'key': 'claimantEmail', 'label': 'Claimant Email', 'location': 19},
  {'key': 'claimantAddress1', 'label': 'Claimant Address1', 'location': 20},
  {'key': 'claimantAddress2', 'label': 'Claimant Address2', 'location': 21},
  {'key': 'claimantCity', 'label': 'Claimant City', 'location': 22},
  {'key': 'claimantState', 'label': 'Claimant State', 'location': 23},
  {'key': 'claimantZip', 'label': 'Claimant Zip', 'location': 24},
  {'key': 'make', 'label': 'Make ', 'location': 25},
  {'key': 'model', 'label': 'Model', 'location': 26},
  {'key': 'year', 'label': 'Year ', 'location': 27},
  {'key': 'vin', 'label': 'Vin ', 'location': 28},
  {'key': 'license', 'label': 'License ', 'location': 29},
  {'key': 'licenseState', 'label': 'License State', 'location': 30},
  {'key': 'dateOfLoss', 'label': 'Date Loss', 'location': 31},
  {'key': 'typeOfLoss', 'label': 'Type Loss', 'location': 32},
  {'key': 'lossDescription', 'label': 'Loss Description', 'location': 33},
  {'key': 'providesCopyOfAppraisal', 'label': 'Provides Appraisal', 'location': 34},
  {'key': 'requestSalvageBids', 'label': 'Request Bids', 'location': 35},
  {'key': 'provideAcvEvaluation', 'label': 'Provide Evaluation', 'location': 36},
  {'key': 'valuationMethod', 'label': 'Valuation Method', 'location': 37},
  {'key': 'vehicleLocationName', 'label': 'Vehicle Name', 'location': 38},
  {'key': 'vehicleLocationAddress1', 'label': 'Vehicle Address1', 'location': 39},
  {'key': 'vehicleLocationAddress2', 'label': 'Vehicle Address2', 'location': 40},
  {'key': 'vehicleLocationCity', 'label': 'Vehicle City', 'location': 41},
  {'key': 'vehicleLocationState', 'label': 'Vehicle State', 'location': 42},
  {'key': 'vehicleLocationZip', 'label': 'Vehicle Zip', 'location': 43},
  {'key': 'vehicleLocationPhone', 'label': 'Vehicle Phone', 'location': 44},
  {'key': 'isRepairFacility', 'label': 'Is Facility', 'location': 45}
];

