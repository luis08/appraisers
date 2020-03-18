import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RequiredUploadsAssignmentComponent } from './required-uploads-assignment.component';

describe('RequiredUploadsAssignmentComponent', () => {
  let component: RequiredUploadsAssignmentComponent;
  let fixture: ComponentFixture<RequiredUploadsAssignmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequiredUploadsAssignmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequiredUploadsAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
