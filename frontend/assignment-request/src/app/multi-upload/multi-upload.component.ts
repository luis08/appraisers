import { Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UploadSelectionService} from "../upload-selection.service";

@Component({
  selector: 'app-multi-upload',
  templateUrl: './multi-upload.component.html',
  styleUrls: ['./multi-upload.component.css']
})
export class MultiUploadComponent implements OnInit {
  @ViewChild("fileUpload", {static: false}) fileUpload: ElementRef;
  files = [];
  nextid: number = 0;

  constructor(private uploadSelectionService: UploadSelectionService) {
  }

  addToupload(file) {
    this.files.push(file);
  }


  onClick() {
    const fileUpload = this.fileUpload.nativeElement;
    fileUpload.onchange = () => {
      for (let index = 0; index < fileUpload.files.length; index++) {
        const file = fileUpload.files[index];
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
  setFiles():void{
    this.uploadSelectionService.setFiles(this.files);
  }
  removeFile(file) {
    let withoutFile = this.files.filter(f => f.id !== file.id);
    this.files = withoutFile;
    this.setFiles();
  }

  ngOnInit() {
  }
}
