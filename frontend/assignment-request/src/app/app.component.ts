import {Component, OnInit} from '@angular/core';
import {AssignmentState, AssignmentStateService} from './assignment-state.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Test secure';
  state: AssignmentState = AssignmentState.Full;
  originalState: AssignmentState = AssignmentState.Full;

  constructor(private assignmentStateService: AssignmentStateService) {
  }

  showFull() {
    return this.state === AssignmentState.Full;
  }

  showMultiUpload() {
    return this.state === AssignmentState.MultiUpload;
  }

  showSuccess() {
    return this.state === AssignmentState.SuccessfulSubmission;
  }

  /**
   * We need the url into this component to be something like
   * something.com/assignment-request/index.html?full
   * or
   * something.com/assignment-request/index.html?multi-upload
   *
   * We are not using routing so it's a mission.
   */

  ngOnInit() {
    this.originalState = this.assignmentStateService.getState(window.location.href) || AssignmentState.Full;
    if(this.originalState === AssignmentState.SuccessfulSubmission) {
      this.originalState = AssignmentState.Full;
    }
    this.state = this.originalState;
    this.assignmentStateService.setState(this.state, null);
    this.assignmentStateService.sharedStateBucket.subscribe(bucket => {
      this.state = bucket.assignmentState;
    });
  }
}
