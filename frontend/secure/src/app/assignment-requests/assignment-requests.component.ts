import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {AssignmentRequest} from '../../../../assignment-request/src/app/assignment-request';
import {HttpClient} from '@angular/common/http';
import paginate from 'jw-paginate';

@Component({
  selector: 'app-assignment-requests',
  templateUrl: './assignment-requests.component.html',
  styleUrls: ['./assignment-requests.component.css']
})
export class AssignmentRequestsComponent implements OnInit,  OnChanges {
  items = [];
  baseUrl = '/assignments';
  pager = {
    pageNumber: 1, pageSize: 10,
    currentPage: [],
    pages: [],
    totalPages: 1,
    pageLinkDisplayCount: 10,
    totalElements: 1
  };
  jwPager: any = {};
  assignmentRequests: AssignmentRequest[];
  assignmentRequest: AssignmentRequest;

  constructor(private http: HttpClient) {
  }

  setPage(pageNum: number) {
    this.pager.pageNumber = pageNum;
    this.getAppraisals();
  }

  getAppraisals() {
    const size = this.pager.pageSize;
    const pageNumber = this.pager.pageNumber - 1;
    this.http.get(this.baseUrl + '?page=' + pageNumber + '&size=' + size).toPromise()
      .then((response: any) => {
        this.assignmentRequests = response.content;
        this._setPager(response);
      });
  }
  ngOnInit() {
    // an example array of 150 items to be paged
    this.items = Array(150).fill(0).map((x, i) => ({id: (i + 1), name: `Item ${i + 1}`}));
    // https://www.baeldung.com/pagination-with-a-spring-rest-api-and-an-angularjs-table
    this.getAppraisals();
  }

  ngOnChanges(changes: SimpleChanges) {
    // reset page if items array has changed
    if (changes.items.currentValue !== changes.items.previousValue) {
      this.setPage(1);
    }
  }

  open(assignmentRequest: AssignmentRequest) {
    this.assignmentRequest = assignmentRequest;
  }

  _setPager(response) {
    this.pager.currentPage = response.content;
    this.pager.totalPages = response.totalPages;
    this.jwPager = paginate(response.totalElements, this.pager.pageNumber, this.pager.pageSize, this.pager.pageLinkDisplayCount);
  }
}
