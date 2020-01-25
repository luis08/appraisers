import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadSelectionService {
  private files = new Array<any>();
  private subject = new Subject<any>();

  constructor() { }

  setFiles(files:Array<any>) {
    if(files){
      this.subject.next(files.map(f => f.data));
    }

  }
  getFiles():Observable<any> {
    return this.subject.asObservable();
  }

  clearFiles() {
    this.subject.next();
  }
}
