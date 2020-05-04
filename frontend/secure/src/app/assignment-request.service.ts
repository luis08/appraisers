import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AssignmentRequest} from '../../../assignment-request/src/app/assignment-request';
import {map} from 'rxjs/operators';

const parsedUrl = new URL(window.location.href);
const baseUrl = parsedUrl.origin.concat('/assignment');

@Injectable({
  providedIn: 'root'
})
export class AssignmentRequestService {

  window: any;
  states: string[] = ['AL', 'AK', 'AZ', 'AR', 'CF', 'CL', 'CT', 'DL', 'DC', 'FL', 'GA', 'HA', 'ID', 'IL', 'IN',
    'IA', 'KA', 'KY', 'LA', 'ME', 'MD', 'MS', 'MC', 'MN', 'MI', 'MO', 'MT', 'NB', 'NV', 'NH', 'NJ', 'NM', 'NY',
    'NC', 'ND', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WN', 'WV', 'WS', 'WY'];

  constructor(
    private http: HttpClient
  ) {
  }

  get(id: number): Observable<AssignmentRequest> {
    const url = baseUrl.concat('/get/' + id.toString());
    return this.http.get(url).pipe(

      map(obj => new AssignmentRequest())
    );
  }
}
