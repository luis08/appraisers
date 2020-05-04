import { TestBed } from '@angular/core/testing';

import { AssignmentRequestService } from './assignment-request.service';

describe('AssignmentRequestService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AssignmentRequestService = TestBed.get(AssignmentRequestService);
    expect(service).toBeTruthy();
  });
});
