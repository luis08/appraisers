import {AssignmentState} from './assignment-state.service';
import {AssignmentRequest} from './assignment-request';

export class AssignmentStateBucket {
  assignmentState: AssignmentState;
  assignmentRequest: AssignmentRequest;

  constructor(assignementState: AssignmentState, assignmentRequest: AssignmentRequest) {
    this.assignmentState = assignementState;
    this.assignmentRequest = assignmentRequest;
  }
}
