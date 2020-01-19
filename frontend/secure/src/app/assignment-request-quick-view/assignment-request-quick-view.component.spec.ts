import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignmentRequestQuickViewComponent } from './assignment-request-quick-view.component';

describe('AssignmentRequestQuickViewComponent', () => {
  let component: AssignmentRequestQuickViewComponent;
  let fixture: ComponentFixture<AssignmentRequestQuickViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignmentRequestQuickViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentRequestQuickViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
