import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Subscription} from 'rxjs';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AssignmentRequest} from '../assignment-request';
import {AssignmentService} from '../assignment.service';
import {AssignmentStateService} from '../assignment-state.service';
import {AssignmentRequestBase} from '../assignment-create/AssignmentRequestBase';

@Component({
  selector: 'app-required-uploads-assignment',
  templateUrl: './required-uploads-assignment.component.html',
  styleUrls: ['./required-uploads-assignment.component.css']
})
export class RequiredUploadsAssignmentComponent extends AssignmentRequestBase implements OnInit {
  @ViewChild('fileUpload1', {static: false}) fileUpload1: ElementRef;
  @ViewChild('fileUpload2', {static: false}) fileUpload2: ElementRef;
  @ViewChild('fileUpload3', {static: false}) fileUpload3: ElementRef;
  @ViewChild('fileUpload4', {static: false}) fileUpload4: ElementRef;
  @ViewChild('fileUpload5', {static: false}) fileUpload5: ElementRef;
  @ViewChild('fileUpload6', {static: false}) fileUpload6: ElementRef;

  fileControls: Array<any>;
  subscription: Subscription;
  registerForm: FormGroup;
  states: string[];
  files: any[];
  nextFileId = -1;
  isNew = true;
  assignmentRequest: AssignmentRequest;
  additionalFilesValue = '';

  constructor(private assigmentService: AssignmentService,
              private formBuilder: FormBuilder,
              private ref: ChangeDetectorRef,
              private assignmentStateService: AssignmentStateService) {
    super();
  }

  ngOnInit(): void {
    this.assignmentStateService.sharedStateBucket.subscribe(bucket => this.assignmentRequest = bucket.assignmentRequest);
    this.resetForm();

  }

  save() {
    this.setupAttachments();
    const assignmentRequest: AssignmentRequest = this.registerForm.value;
    assignmentRequest.uploadingFiles = this.files;
    this.setWaiting();
    this.assigmentService.create(assignmentRequest).toPromise()
      .then((ar: AssignmentRequest) => {
        this.assignmentStateService.successfullySubmitted(ar);
        this.setSuccess();
      }).catch((reason: any) => {
        this.setFailed();
      });
  }

  private setupAttachments(): void {
    this.fileControls = new Array<any>();
    this.fileControls.push(this.fileUpload1.nativeElement);
    this.fileControls.push(this.fileUpload2.nativeElement);
    this.fileControls.push(this.fileUpload3.nativeElement);
    this.fileControls.push(this.fileUpload4.nativeElement);
    this.fileControls.push(this.fileUpload5.nativeElement);
    this.fileControls.push(this.fileUpload6.nativeElement);

    this.files = new Array<any>();
    this.fileControls.filter((f) => f.files.length > 0)
      .forEach((f) => this.files.push(f.files[0]));
  }

  private resetForm(): void {
    this.isNew = !this.assignmentRequest;
    this.nextFileId = -1;
    this.files = new Array<any>();
    this.states = this.assigmentService.getStates();
    this.registerForm = this.formBuilder.group({
      adjusterEmail: [''],
      adjusterFirstName: [''],
      adjusterLastName: [''],
      adjusterPhone: [''],
      companyAddress1: [''],
      companyAddress2: [''],
      companyName: [''],
      companyCity: [''],
      companyState: [''],
      companyZip: ['']
    });
  }
}
