import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AssignmentCreateComponent } from './assignment-create/assignment-create.component';
import {ReactiveFormsModule} from "@angular/forms";
import { MultiUploadComponent } from './multi-upload/multi-upload.component';


@NgModule({
  declarations: [
    AppComponent,
    AssignmentCreateComponent,
    MultiUploadComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
