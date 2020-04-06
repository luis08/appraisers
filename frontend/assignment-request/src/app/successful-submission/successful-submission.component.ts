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
    this.originalState = window.location.href;
  }

  reset(): void {
    this.assignmentStateService.reset(this.originalState);
    window.location.href = this.originalState;
  }
}
