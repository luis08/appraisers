import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AssignmentRequest} from './assignment-request';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';

const parsedUrl = new URL(window.location.href);
const baseUrl = parsedUrl.origin.concat('/assignment');
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

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

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
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

    return this.http.post(url, formData)
      .pipe(
        catchError(this.handleError<AssignmentRequest>(`save`))
      );
  }

  getStates(): string[] {
    return this.states;
  }
}
