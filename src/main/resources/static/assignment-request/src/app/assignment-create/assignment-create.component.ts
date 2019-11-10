import {Component, OnInit} from '@angular/core';
import {AssignmentRequest} from "../assignmentRequest";
import {AssignmentServiceService} from '../assignment-service.service';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';

@Component({
  selector: 'app-assignment-create',
  templateUrl: './assignment-create.component.html',
  styleUrls: ['./assignment-create.component.css']
})
export class AssignmentCreateComponent implements OnInit {
  registerForm: FormGroup;
  states: string[];

  constructor(private assigmentService: AssignmentServiceService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.states = this.assigmentService.getStates();
    this.registerForm = this.formBuilder.group({
      uploadingFiles: [[]],
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

  _doSave() {
    let assignmentRequest = this.registerForm.value;
    this.assigmentService.saveAssignment(assignmentRequest).toPromise()
      .then(() => {
        console.log('done');
        return null;
      });;
    console.log(assignmentRequest);
  }

  save() {
    if (this.registerForm.valid) {
      this._doSave();
    } else {
      console.log(this.registerForm.value);
      alert("There is an error");
    }
  }

  getFormValidationErrors() {
    let form = this.registerForm;
    const result = [];
    Object.keys(form.controls).forEach(key => {

      const controlErrors: ValidationErrors = form.get(key).errors;
      if (controlErrors) {
        Object.keys(controlErrors).forEach(keyError => {
          result.push({
            'control': key,
            'error': keyError,
            'value': controlErrors[keyError]
          });
        });
      }
    });

    return result;
  }
}
