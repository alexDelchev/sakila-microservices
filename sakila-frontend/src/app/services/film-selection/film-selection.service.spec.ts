import { TestBed } from '@angular/core/testing';

import { FilmSelectionService } from './film-selection.service';

describe('FilmSelectionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FilmSelectionService = TestBed.get(FilmSelectionService);
    expect(service).toBeTruthy();
  });
});
