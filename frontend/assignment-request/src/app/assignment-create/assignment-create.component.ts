import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {AssignmentRequest} from "../assignment-request"
import {AssignmentService} from '../assignment.service';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';

@Component({
  selector: 'app-assignment-create',
  templateUrl: './assignment-create.component.html',
  styleUrls: ['./assignment-create.component.css']
})
export class AssignmentCreateComponent implements OnInit {
  registerForm: FormGroup;
  states: string[];
  files: any[];

  constructor(private assigmentService: AssignmentService,
              private formBuilder: FormBuilder,
              private ref: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.files = new Array<string>();
    this.states = this.assigmentService.getStates();
    this.registerForm = this.formBuilder.group({
      accountNumber: [''],
      adjusterEmail: ['', Validators.required],
      adjusterFirstName: ['', Validators.required],
      adjusterLastName: ['', Validators.required],
      adjusterPhone: ['', Validators.required],
      claimantAddress1: [''],
      claimantAddress2: [''],
      claimantCity: [''],
      claimantEmail: [''],
      claimantFirst: [''],
      claimantLast: [''],
      claimantPhone: [''],
      claimantState: [''],
      claimantZip: [''],
      claimNumber: ['', Validators.required],
      companyAddress1: ['', Validators.required],
      companyAddress2: [''],
      companyName: ['', Validators.required],
      companyCity: ['', Validators.required],
      companyState: ['', Validators.required],
      companyZip: ['', Validators.required],
      dateOfLoss: [''],
      deductibleAmount: [''],
      insuredClaimantSameAsOwner: [''],
      insuredOrClaimant: [''],
      isRepairFacility: [''],
      license: [''],
      licenseState:[''],
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
  }


  _getIt() : AssignmentRequest {
    let assignmentRequest: AssignmentRequest = this.registerForm.value;

    return null;
  }

  _getAssignmentRequest() {
    let assignmentRequest: AssignmentRequest = this.registerForm.value;
    assignmentRequest.uploadingFiles = this.files;
    return assignmentRequest;
  }

  create() {
    let assignmentRequest: AssignmentRequest = this._getAssignmentRequest();
    this.assigmentService.create(assignmentRequest).toPromise()
      .then((ar) => {
        console.log('done');
        console.log(ar);
      });
  }

  onFileSelectionChange(event) {
    if (event.target && event.target.files.length) {
      for (let f of event.target.files) {
        this.files.push(f);
      }
    }
  }

  /**
   * Testing TODO: Remove
   */
  populate() {
    this.registerForm.setValue({accountNumber:"accountNumber_val",
      adjusterEmail:"adjuster@nowhere.com",
      adjusterFirstName:"adjusterFirstName_val",
      adjusterLastName:"adjusterLastName_val",
      adjusterPhone:"305-111-1111",
      claimantAddress1:"claimantAddress1_val",
      claimantAddress2:"claimantAddress2_val",
      claimantCity:"claimantCity_val",
      claimantEmail:"claimant@nowhere.com",
      claimantFirst:"claimantFirst_val",
      claimantLast:"claimantLast_val",
      claimantPhone:"305-111-1112",
      claimantState:"CO",
      claimantZip:"22222",
      claimNumber:"claimNumber_val",
      companyAddress1:"companyAddress1_val",
      companyAddress2:"companyAddress2_val",
      companyName:"companyName_val",
      companyCity:"companyCity_val",
      companyState:"CT",
      companyZip:"33333",
      dateOfLoss:"12/01/2019",
      deductibleAmount:"100",
      insuredClaimantSameAsOwner:"FALSE",
      insuredOrClaimant:"Claimant",
      isRepairFacility:"TRUE",
      license:"license_val",
      licenseState: "AK",
      locationState:"AZ",
      lossDescription:"lossDescription_val",
      make:"make_val",
      model:"model_val",
      policyNumber:"policyNumber_val",
      provideAcvEvaluation:"TRUE",
      providesCopyOfAppraisal:"FALSE",
      requestSalvageBids:"TRUE",
      typeOfLoss:"typeOfLoss_val",
      valuation:"valuation_val",
      valuationMethod:"valuationMethod_val",
      vehicleLocationAddress1:"vehicleLocationAddress1_val",
      vehicleLocationAddress2:"vehicleLocationAddress2_val",
      vehicleLocationCity:"vehicleLocationCity_val",
      vehicleLocationName:"vehicleLocationName_val",
      vehicleLocationPhone:"vehicleLocationPhone_val",
      vehicleLocationState:"CA",
      vehicleLocationZip:"44444",
      vin:"vin_val",
      year:1991});
    console.log(this.registerForm.value);
  }

}