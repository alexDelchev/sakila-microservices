import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilmBrowserComponent } from './film-browser.component';

describe('FilmBrowserComponent', () => {
  let component: FilmBrowserComponent;
  let fixture: ComponentFixture<FilmBrowserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilmBrowserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilmBrowserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
