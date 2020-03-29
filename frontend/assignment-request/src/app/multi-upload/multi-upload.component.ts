import { Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UploadSelectionService} from "../upload-selection.service";
import {AssignmentStateService} from "../assignment-state.service";

@Component({
  selector: 'app-multi-upload',
  templateUrl: './multi-upload.component.html',
  styleUrls: ['./multi-upload.component.css']
})
export class MultiUploadComponent implements OnInit {
  @ViewChild('fileUpload', {static: false}) fileUpload: ElementRef;
  files = [];
  nextid = 0;

  constructor(private uploadSelectionService: UploadSelectionService, private assignmentStateService: AssignmentStateService) {
  }

  addToupload(file) {
    this.files.push(file);
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
    console.log('called click');
  }
  setFiles(): void {
    this.uploadSelectionService.setFiles(this.files);
  }
  removeFile(file) {
    const withoutFile = this.files.filter(f => f.id !== file.id);
    this.files = withoutFile;
    this.setFiles();
  }

  ngOnInit() {
  }
}
