import {Component} from '@angular/core';
import {AssignmentState} from './assignment-state.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent {
  title = 'Test secure';
  state: AssignmentState = AssignmentState.Full;

  showFull() {
    return this.state === AssignmentState.Full;
  }

  showMultiUpload() {
    return this.state === AssignmentState.MultiUpload;
  }

  showSuccess() {
    return this.state === AssignmentState.SuccessfulSubmission;
  }
}
