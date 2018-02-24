import { TestBed, inject } from '@angular/core/testing';

import { ShowTaskService } from './show-task.service';

describe('ShowTaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ShowTaskService]
    });
  });

  it('should be created', inject([ShowTaskService], (service: ShowTaskService) => {
    expect(service).toBeTruthy();
  }));
});
