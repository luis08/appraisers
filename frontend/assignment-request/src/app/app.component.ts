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

  ngOnInit() {
    this.assignmentStateService.sharedState.subscribe(state => this.state = state);
  }
}
