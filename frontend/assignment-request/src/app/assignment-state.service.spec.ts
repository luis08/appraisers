import { TestBed } from '@angular/core/testing';

import { AssignmentStateService } from './assignment-state.service';

describe('AssignmentStateService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AssignmentStateService = TestBed.get(AssignmentStateService);
    expect(service).toBeTruthy();
  });
});
