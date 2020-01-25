import { TestBed } from '@angular/core/testing';

import { UploadSelectionService } from './upload-selection.service';

describe('UploadSelectionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UploadSelectionService = TestBed.get(UploadSelectionService);
    expect(service).toBeTruthy();
  });
});
