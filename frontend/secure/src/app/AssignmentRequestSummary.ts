import {DomainComponent} from './DomainComponent';

export class AssignmentRequestSummary {
  assignmentRequestId: number;
  identifier: string;
  assignmentRequestMutationId: number;
  companyName: string;
  accountNumber: string;
  updateCount: number
  updates: Array<DomainComponent>;
  adjusterName: string;
}
