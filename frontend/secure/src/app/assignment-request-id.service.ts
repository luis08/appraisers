import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AssignmentRequestIdService {

  constructor() { }

  private assignmentRequestIdSource: BehaviorSubject<number> =
    new BehaviorSubject<number>(null);
  assignmentRequestId = this.assignmentRequestIdSource.asObservable();

  set(assignmentRequestId: number): void {
    this.assignmentRequestIdSource.next(assignmentRequestId);
  }

  clear(): void {
    this.assignmentRequestIdSource.next(null);
  }
}
