import { TestBed, inject } from '@angular/core/testing';

import { SbatchService } from './sbatch.service';

describe('SbatchService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SbatchService]
    });
  });

  it('should be created', inject([SbatchService], (service: SbatchService) => {
    expect(service).toBeTruthy();
  }));
});
