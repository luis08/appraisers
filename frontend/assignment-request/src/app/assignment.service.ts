import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AssignmentRequest} from './assignment-request';
import {Observable} from 'rxjs';

const parsedUrl = new URL(window.location.href);
const baseUrl = parsedUrl.origin.concat('/assignment');

@Injectable({
  providedIn: 'root'
})
export class AssignmentService {
  window: any;
  states: string[] = ['AL', 'AK', 'AZ', 'AR', 'CF', 'CL', 'CT', 'DL', 'DC', 'FL', 'GA', 'HA', 'ID', 'IL', 'IN',
    'IA', 'KA', 'KY', 'LA', 'ME', 'MD', 'MS', 'MC', 'MN', 'MI', 'MO', 'MT', 'NB', 'NV', 'NH', 'NJ', 'NM', 'NY',
    'NC', 'ND', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WN', 'WV', 'WS', 'WY'];

  constructor(
    private http: HttpClient
  ) {
  }

  create(assignmentRequest: AssignmentRequest): Observable<unknown> {
    const url = baseUrl.concat('/create');
    const formData = new FormData();
    assignmentRequest.uploadingFiles.forEach(f => {
      delete f.id;
      formData.append('files', f);
    });
    assignmentRequest.uploadingFiles = [];
    const serializedAssignmentRequest = new Blob([JSON.stringify(assignmentRequest)],
      {type: 'application/json'});
    formData.append('assignmentRequest', serializedAssignmentRequest);

    return this.http.post(url, formData);
  }

  getStates(): string[] {
    return this.states;
  }
}
