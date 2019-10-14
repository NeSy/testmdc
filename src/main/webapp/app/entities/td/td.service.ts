import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITd } from 'app/shared/model/td.model';

type EntityResponseType = HttpResponse<ITd>;
type EntityArrayResponseType = HttpResponse<ITd[]>;

@Injectable({ providedIn: 'root' })
export class TdService {
  public resourceUrl = SERVER_API_URL + 'api/tds';

  constructor(protected http: HttpClient) {}

  create(td: ITd): Observable<EntityResponseType> {
    return this.http.post<ITd>(this.resourceUrl, td, { observe: 'response' });
  }

  update(td: ITd): Observable<EntityResponseType> {
    return this.http.put<ITd>(this.resourceUrl, td, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITd>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITd[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
