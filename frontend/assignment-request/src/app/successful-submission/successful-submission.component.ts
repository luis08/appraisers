import { Component, OnInit } from '@angular/core';
import {AssignmentStateService} from "../assignment-state.service";
import {AssignmentRequest} from "../assignment-request";

@Component({
  selector: 'app-successful-submission',
  templateUrl: './successful-submission.component.html',
  styleUrls: ['./successful-submission.component.css']
})
export class SuccessfulSubmissionComponent implements OnInit {
  assignmentRequest: AssignmentRequest;

  constructor(private assignmentStateService: AssignmentStateService) { }

  ngOnInit() {
    this.assignmentStateService.sharedStateBucket.subscribe(bucket => this.assignmentRequest = bucket.assignmentRequest);
  }

}
