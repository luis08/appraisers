import {Component, OnInit} from '@angular/core';
import {AssignmentStateService} from '../assignment-state.service';
import {AssignmentStateBucket} from '../AssignmentStateBucket';

@Component({
  selector: 'app-successful-submission',
  templateUrl: './successful-submission.component.html',
  styleUrls: ['./successful-submission.component.css']
})
export class SuccessfulSubmissionComponent implements OnInit {
  assignmentRequestBucket: AssignmentStateBucket;
  originalState: string;

  constructor(private assignmentStateService: AssignmentStateService) {
  }

  ngOnInit() {
    this.assignmentStateService.sharedStateBucket.subscribe(bucket => this.assignmentRequestBucket = bucket);
    const pathArray = window.location.pathname.split('/');
    pathArray.forEach(a => console.log(a));
    const statesFound = pathArray.filter(a => this.assignmentStateService.getState(a) !== null);
    if (statesFound && statesFound.length > 0) {
      this.originalState = statesFound[0];
    }
  }

  reset(): void {
    this.assignmentStateService.reset(this.originalState);
  }
}
