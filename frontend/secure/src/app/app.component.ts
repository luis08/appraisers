import {Component, OnInit} from '@angular/core';
import {AssignmentRequestIdService} from './assignment-request-id.service';

// TODO: Remove the garbage of the original pager
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html'
})
export class AppComponent implements OnInit {
  assignmentRequestId: number;

  constructor(private assignmentRequestIdService: AssignmentRequestIdService) {
  }

  ngOnInit(): void {
    this.assignmentRequestIdService.assignmentRequestId.subscribe(
      (assignmentRequestId: number) => {
        this.assignmentRequestId = assignmentRequestId
        window.scroll(0,0);
      });
  }
}
