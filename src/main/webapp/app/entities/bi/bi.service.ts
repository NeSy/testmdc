import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBi } from 'app/shared/model/bi.model';

type EntityResponseType = HttpResponse<IBi>;
type EntityArrayResponseType = HttpResponse<IBi[]>;

@Injectable({ providedIn: 'root' })
export class BiService {
  public resourceUrl = SERVER_API_URL + 'api/bis';

  constructor(protected http: HttpClient) {}

  create(bi: IBi): Observable<EntityResponseType> {
    return this.http.post<IBi>(this.resourceUrl, bi, { observe: 'response' });
  }

  update(bi: IBi): Observable<EntityResponseType> {
    return this.http.put<IBi>(this.resourceUrl, bi, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
