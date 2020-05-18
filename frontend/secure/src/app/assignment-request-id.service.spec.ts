import { TestBed } from '@angular/core/testing';

import { AssignmentRequestIdService } from './assignment-request-id.service';

describe('AssignmentRequestIdService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AssignmentRequestIdService = TestBed.get(AssignmentRequestIdService);
    expect(service).toBeTruthy();
  });
});
