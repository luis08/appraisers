import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AssignmentCreateComponent } from './assignment-create/assignment-create.component';
import {ReactiveFormsModule} from '@angular/forms';
import { MultiUploadComponent } from './multi-upload/multi-upload.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule,
  MatIconModule,
  MatCardModule,
  MatButtonModule,
  MatProgressBarModule } from '@angular/material';
import { RequiredUploadsAssignmentComponent } from './required-uploads-assignment/required-uploads-assignment.component';
import { SuccessfulSubmissionComponent } from './successful-submission/successful-submission.component';
import { UploadOnlyComponent } from './upload-only/upload-only.component';

@NgModule({
  declarations: [
    AppComponent,
    AssignmentCreateComponent,
    MultiUploadComponent,
    RequiredUploadsAssignmentComponent,
    SuccessfulSubmissionComponent,
    UploadOnlyComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatProgressBarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
