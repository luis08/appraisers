import { Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UploadSelectionService} from '../upload-selection.service';
import {AssignmentStateService} from '../assignment-state.service';
import {AssignmentRequest} from '../assignment-request';

@Component({
  selector: 'app-multi-upload',
  templateUrl: './multi-upload.component.html',
  styleUrls: ['./multi-upload.component.css']
})
export class MultiUploadComponent implements OnInit {
  @ViewChild('fileUpload', {static: false}) fileUpload: ElementRef;
  files = [];
  nextid = 0;
  assignmentRequest: AssignmentRequest;

  constructor(private uploadSelectionService: UploadSelectionService,
              private assignmentStateService: AssignmentStateService) {
  }

  onClick() {
    const fileUpload = this.fileUpload.nativeElement;
    fileUpload.onchange = () => {
      for (const file of fileUpload.files) {
        this.files.push({
          id: this.nextid++,
          data: file,
          inProgress: false,
          progress: 0
        });
        this.setFiles();
      }
    };
    fileUpload.click();
  }
  setFiles(): void {
    this.uploadSelectionService.setFiles(this.files);
  }

  removeFile(file) {
    this.files = this.files.filter(f => f.id !== file.id);
    this.setFiles();
  }

  ngOnInit() {
    this.assignmentStateService.sharedStateBucket.subscribe(bucket => this.assignmentRequest = bucket.assignmentRequest);
  }
}
