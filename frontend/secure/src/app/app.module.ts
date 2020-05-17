import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {ReactiveFormsModule} from '@angular/forms';
import {JwPaginationComponent} from 'jw-angular-pagination';
import { AssignmentRequestQuickViewComponent } from './assignment-request-quick-view/assignment-request-quick-view.component';
import { AssignmentRequestComponent } from './assignment-request/assignment-request.component';
import { AssignmentRequestsComponent } from './assignment-requests/assignment-requests.component';
import { MultiUploadComponent } from './multi-upload/multi-upload.component';


@NgModule({
  declarations: [
    AppComponent,
    JwPaginationComponent,
    HomeComponent,
    AssignmentRequestQuickViewComponent,
    AssignmentRequestComponent,
    AssignmentRequestsComponent,
    MultiUploadComponent
  ],
  imports: [
    BrowserModule, HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
