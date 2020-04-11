import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {AssignmentStateBucket} from './AssignmentStateBucket';
import {AssignmentRequest} from './assignment-request';

@Injectable({
  providedIn: 'root'
})
export class AssignmentStateService {

  constructor() {
  }

  private stateBucketSource: BehaviorSubject<AssignmentStateBucket> =
    new BehaviorSubject(new AssignmentStateBucket(AssignmentState.Full, null));

  sharedStateBucket = this.stateBucketSource.asObservable();

  setSuccessful(): void {
    this.stateBucketSource.getValue().assignmentState = AssignmentState.SuccessfulSubmission;
  }

  setFull() {
    const assignmentStateBucket = new AssignmentStateBucket(AssignmentState.Full, null);
    this.stateBucketSource.next(assignmentStateBucket);
  }

  setMultiUpload() {
    this.stateBucketSource = new BehaviorSubject(new AssignmentStateBucket(AssignmentState.MultiUpload, null));
  }

  successfullySubmitted(assignmentRequest: AssignmentRequest): void {
    const bucket = new AssignmentStateBucket(AssignmentState.SuccessfulSubmission, assignmentRequest);
    this.stateBucketSource.next(bucket);
  }

  reset(fullUrl: string) {
    const pathArray = fullUrl.split('/');
    const statesFound = pathArray.map(a => this.getState(a)).filter(s => s !== null);
    const bucket = new AssignmentStateBucket(AssignmentState.Full, null);
    if (statesFound && statesFound.length === 0) {
      bucket.assignmentState = statesFound[0];
    } else {
      bucket.assignmentState = AssignmentState.Full;
    }
    this.stateBucketSource.next(bucket);
  }

  getState(fullUrl: string): AssignmentState {
    if (!fullUrl || !fullUrl.length) {
      return AssignmentState.MultiUpload;
    }
    const urlArr = fullUrl.split('?');
    if (urlArr.length > 1) {
      return this.getStateFromUrl(urlArr[1]);
    } else {
      // This doesn't make much sense, but maybe... god knows
      return this.getStateFromUrl(urlArr[0]);
    }
  }

  getStateFromUrl(urlPart: string) {
    if (!urlPart) {
      return null;
    } else if (urlPart.toLocaleLowerCase() === 'multi-upload') {
      return AssignmentState.MultiUpload;
    } else if (urlPart.toLocaleLowerCase() === 'full') {
      return AssignmentState.Full;
    } else {
      return null;
    }
  }

  setState(state: AssignmentState, assignmentRequest: AssignmentRequest) {
    this.stateBucketSource.next(new AssignmentStateBucket(state, assignmentRequest));
  }
}

export enum AssignmentState {
  Full,
  MultiUpload,
  SuccessfulSubmission
}
