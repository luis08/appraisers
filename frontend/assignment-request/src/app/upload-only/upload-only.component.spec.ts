import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadOnlyComponent } from './upload-only.component';

describe('UploadOnlyComponent', () => {
  let component: UploadOnlyComponent;
  let fixture: ComponentFixture<UploadOnlyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadOnlyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadOnlyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
