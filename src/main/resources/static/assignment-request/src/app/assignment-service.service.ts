import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AssignmentServiceService {
  let window: any;
  const parsedUrl = new URL(window.location.href);
  const baseUrl = parsedUrl.origin

  constructor(){}
}
