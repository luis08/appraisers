import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AssignmentStateService {

  constructor() {
  }

  private state = new BehaviorSubject(AssignmentState.Full);
  sharedState = this.state.asObservable();

  setState(assignmentState: AssignmentState) {
    this.state.next(assignmentState);
  }

  submittedSuccessfully() {
    this.state.next(AssignmentState.SuccessfulSubmission);
    console.log('The state is: ' + this.state);
  }

  reset() {
    this.state.next(AssignmentState.Full);
  }
}

export enum AssignmentState {
  Full,
  MultiUpload,
  SuccessfulSubmission
}
