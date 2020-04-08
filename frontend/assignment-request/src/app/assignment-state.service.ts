import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {AssignmentStateBucket} from "./AssignmentStateBucket";
import {AssignmentRequest} from "./assignment-request";

@Injectable({
  providedIn: 'root'
})
export class AssignmentStateService {
  private assignmentStateBucket = new AssignmentStateBucket();
  private stateBucketSource: BehaviorSubject<AssignmentStateBucket> = new BehaviorSubject(this.assignmentStateBucket);

  constructor() {
    this.assignmentStateBucket.assignmentState = AssignmentState.Full;
  }

  sharedStateBucket = this.stateBucketSource.asObservable();

  setFull() {
    this.assignmentStateBucket = new AssignmentStateBucket();
    this.assignmentStateBucket.assignmentState = AssignmentState.Full;
    this.stateBucketSource.next(this.assignmentStateBucket);
  }

  setMultiUpload() {
    const stateBucket = new AssignmentStateBucket();
    stateBucket.assignmentState = AssignmentState.MultiUpload;
    this.stateBucketSource = new BehaviorSubject(stateBucket);
  }

  successfullySubmitted(assginmentRequest: AssignmentRequest): void {
    const bucket = new AssignmentStateBucket();
    bucket.assignmentState = AssignmentState.SuccessfulSubmission;
    bucket.assignmentRequest = assginmentRequest;
    this.stateBucketSource.next(bucket);
  }

  reset(fullUrl: string) {
    const pathArray = fullUrl.split('/');
    pathArray.forEach(a => console.log(a));
    const statesFound = pathArray.map(a => this.getState(a)).filter(s => s !== null);
    const bucket = new AssignmentStateBucket();
    if (statesFound && statesFound.length === 0) {
      bucket.assignmentState = statesFound[0];
    } else {
      bucket.assignmentState = AssignmentState.Full;
    }
    this.stateBucketSource.next(bucket);
  }

  getState(fullUrl: string): AssignmentState {
    if(!fullUrl || ! fullUrl.length) {
      return AssignmentState.MultiUpload;
    }
    let urlArr = fullUrl.split('?');
    if(urlArr.length > 1) {
      return this.getStateFromUrl(urlArr[1]);
    } else {
      //This doesn't make much sense, but maybe... god knows
      return this.getStateFromUrl(urlArr[0]);
    }
  }

  getStateFromUrl(urlPart: string) {
    if (!urlPart) {
      return null;
    } else if (urlPart.toLocaleLowerCase() === 'multi-upload') {
      return AssignmentState.MultiUpload;
    } else if(urlPart.toLocaleLowerCase() === 'full') {
      return AssignmentState.Full;
    } else {
      return null;
    }
  }

  static redirectSetters(state: AssignmentState, service: AssignmentStateService) {
    switch (state) {
      case AssignmentState.Full:
        service.setFull();
        return;
      case AssignmentState.MultiUpload:
        service.setMultiUpload();
        return;
    }
  }
}

export enum AssignmentState {
  Full,
  MultiUpload,
  SuccessfulSubmission
}
