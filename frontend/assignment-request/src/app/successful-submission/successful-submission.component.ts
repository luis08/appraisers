import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-successful-submission',
  templateUrl: './successful-submission.component.html',
  styleUrls: ['./successful-submission.component.css']
})
export class SuccessfulSubmissionComponent implements OnInit {
  assignmentRequestIdentifier: string;

  constructor() { }

  ngOnInit() {
  }

}
