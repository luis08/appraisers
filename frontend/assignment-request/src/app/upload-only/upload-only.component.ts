import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {AssignmentRequestBase} from '../../common/AssignmentRequestBase';
import {AssignmentService} from '../assignment.service';
import {UploadSelectionService} from '../upload-selection.service';
import {AssignmentStateService} from '../assignment-state.service';
import {Subscription} from 'rxjs/internal/Subscription';
import {AssignmentRequest} from '../assignment-request';

@Component({
  selector: 'app-upload-only',
  templateUrl: './upload-only.component.html',
  styleUrls: ['./upload-only.component.css']
})
export class UploadOnlyComponent extends AssignmentRequestBase implements OnInit {
  subscription: Subscription;
  states: string[];
  files: any[];
  nextFileId = -1;
  assignmentRequest: AssignmentRequest;

  constructor(private assigmentService: AssignmentService,
              private uploadSelectionService: UploadSelectionService,
              private ref: ChangeDetectorRef,
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
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  create() {
    const assignmentRequest: AssignmentRequest = this._getAssignmentRequest();
    this.setWaiting();
    this.assigmentService.create(assignmentRequest).toPromise()
      .then((ar: AssignmentRequest) => {
        this.assignmentStateService.successfullySubmitted(ar);
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

  private _getAssignmentRequest() {
    const assignmentRequest = new AssignmentRequest();
    assignmentRequest.uploadingFiles = this.files;
    return assignmentRequest;
  }
}
