import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpErrorResponse, HttpEventType } from  '@angular/common/http';
import { map } from  'rxjs/operators';

const parsedUrl = new URL(window.location.href);
const baseUrl = parsedUrl.origin.concat("/assignment");

@Injectable({
  providedIn: 'root'
})
export class UploaderService {

  constructor(private httpClient: HttpClient) { }
  public upload(formData) {

    return this.httpClient.post<any>(baseUrl, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }
}
