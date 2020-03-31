import {Injectable, OnInit} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {AssignmentStateBucket} from "./AssignmentStateBucket";
import {AssignmentRequest} from "./assignment-request";

@Injectable({
  providedIn: 'root'
})
export class AssignmentStateService implements OnInit {

  constructor() {
  }

  private stateBucketSource: BehaviorSubject<AssignmentStateBucket>;
  sharedStateBucket = this.stateBucketSource.asObservable();

  _initializeBucket() {
    let stateBucket = new AssignmentStateBucket();
    stateBucket.assignmentState = AssignmentState.Full;
    this.stateBucketSource = new BehaviorSubject(stateBucket);
  }

  setFull() {
    this._initializeBucket();
  }

  setMultiUpload() {
    let stateBucket = new AssignmentStateBucket();
    stateBucket.assignmentState = AssignmentState.MultiUpload;
    this.stateBucketSource = new BehaviorSubject(stateBucket);
  }

  successfullySubmitted(assginmentRequest: AssignmentRequest) :void {
    let bucket = new AssignmentStateBucket();
    bucket.assignmentState = AssignmentState.SuccessfulSubmission;
    bucket.assignmentRequest = assginmentRequest;
    this.stateBucketSource.next(bucket);
  }

  reset() {
    this._initializeBucket();
  }

  ngOnInit(): void {
    this._initializeBucket();
  }
}

export enum AssignmentState {
  Full,
  MultiUpload,
  SuccessfulSubmission
}
