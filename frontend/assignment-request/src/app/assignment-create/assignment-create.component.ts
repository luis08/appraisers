import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {AssignmentRequest} from '../assignment-request';
import {AssignmentService} from '../assignment.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs/internal/Subscription';
import {UploadSelectionService} from '../upload-selection.service';
import {AssignmentStateService} from '../assignment-state.service';
import {AssignmentRequestBase} from '../../common/AssignmentRequestBase';

@Component({
  selector: 'app-assignment-create',
  templateUrl: './assignment-create.component.html',
  styleUrls: ['./assignment-create.component.css']
})
export class AssignmentCreateComponent extends AssignmentRequestBase implements OnInit, OnDestroy {
  subscription: Subscription;
  registerForm: FormGroup;
  states: string[];
  files: any[];
  nextFileId = -1;
  assignmentRequest: AssignmentRequest;

  constructor(private assigmentService: AssignmentService,
              private formBuilder: FormBuilder,
              private ref: ChangeDetectorRef,
              private uploadSelectionService: UploadSelectionService,
              private assignmentStateService: AssignmentStateService) {
    super();
  }

  ngOnInit() {
    this.assignmentStateService.sharedStateBucket.subscribe(bucket => this.assignmentRequest = bucket.assignmentRequest);
    this.subscription = this.uploadSelectionService.getFiles().subscribe(files => {
      if (files) {
        this.files = files;
      } else {
        this.files = [];
      }
    });
    this._resetForm();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  _resetForm() {
    this.nextFileId = -1;
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
  }

  _getAssignmentRequest() {
    const assignmentRequest: AssignmentRequest = this.registerForm.value;
    assignmentRequest.uploadingFiles = this.files;
    return assignmentRequest;
  }

  create() {
    const assignmentRequest: AssignmentRequest = this._getAssignmentRequest();
    this.setWaiting();
    this.assigmentService.create(assignmentRequest).toPromise()
      .then((ar: AssignmentRequest) => {
        this.assignmentStateService.successfullySubmitted(ar);
        this._resetForm();
        this.uploadSelectionService.clearFiles();
        this.setSuccess();
      }).catch((reason: any) => {
        this.setFailed();
      });
  }

  removeFile(file) {
    if (file && file.id) {
      this.files = this.files.filter(f => f.id !== file.id);
    }
  }

  onFileSelectionChange(event) {
    if (event.target && event.target.files.length) {
      for (const f of event.target.files) {
        f.id = this.nextFileId--;
        this.files.push(f);
      }
    }
  }

  onAdditionalFilesSelectionChange(event) {
    if (event.target && event.target.files.length) {
      for (const f of event.target.files) {
        f.id = this.nextFileId--;
        this.files.push(f);
      }
    }
  }

  /**
   * Testing TODO: Remove Populate
   */
  populate() {
    this.registerForm.setValue({
      adjusterEmail: 'adjuster@nowhere.com',
      accountNumber: 'accountNumber_val',
      adjusterFirstName: 'adjusterFirstName_val',
      adjusterLastName: 'adjusterLastName_val',
      adjusterPhone: '305-111-1111',
      claimantAddress1: 'claimantAddress1_val',
      claimantAddress2: 'claimantAddress2_val',
      claimantCity: 'claimantCity_val',
      claimantEmail: 'claimant@nowhere.com',
      claimantFirst: 'claimantFirst_val',
      claimantLast: 'claimantLast_val',
      claimantPhone: '305-111-1112',
      claimantState: 'CO',
      claimantZip: '22222',
      claimNumber: 'claimNumber_val',
      companyAddress1: 'companyAddress1_val',
      companyAddress2: 'companyAddress2_val',
      companyName: 'companyName_val',
      companyCity: 'companyCity_val',
      companyState: 'CT',
      companyZip: '33333',
      dateOfLoss: '12/01/2019',
      deductibleAmount: '100',
      insuredClaimantSameAsOwner: 'FALSE',
      insuredOrClaimant: 'Claimant',
      isRepairFacility: 'TRUE',
      license: 'license_val',
      licenseState: 'AK',
      locationState: 'AZ',
      lossDescription: 'lossDescription_val',
      make: 'make_val',
      model: 'model_val',
      policyNumber: 'policyNumber_val',
      provideAcvEvaluation: 'TRUE',
      providesCopyOfAppraisal: 'FALSE',
      requestSalvageBids: 'TRUE',
      typeOfLoss: 'typeOfLoss_val',
      valuation: 'valuation_val',
      valuationMethod: 'valuationMethod_val',
      vehicleLocationAddress1: 'vehicleLocationAddress1_val',
      vehicleLocationAddress2: 'vehicleLocationAddress2_val',
      vehicleLocationCity: 'vehicleLocationCity_val',
      vehicleLocationName: 'vehicleLocationName_val',
      vehicleLocationPhone: 'vehicleLocationPhone_val',
      vehicleLocationState: 'CA',
      vehicleLocationZip: '44444',
      vin: 'vin_val',
      year: 1991
    });
  }
}
