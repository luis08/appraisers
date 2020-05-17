import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignmentRequestsComponent } from './assignment-requests.component';

describe('AssignmentRequestsComponent', () => {
  let component: AssignmentRequestsComponent;
  let fixture: ComponentFixture<AssignmentRequestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignmentRequestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
