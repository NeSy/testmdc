import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEqpt } from 'app/shared/model/eqpt.model';

type EntityResponseType = HttpResponse<IEqpt>;
type EntityArrayResponseType = HttpResponse<IEqpt[]>;

@Injectable({ providedIn: 'root' })
export class EqptService {
  public resourceUrl = SERVER_API_URL + 'api/eqpts';

  constructor(protected http: HttpClient) {}

  create(eqpt: IEqpt): Observable<EntityResponseType> {
    return this.http.post<IEqpt>(this.resourceUrl, eqpt, { observe: 'response' });
  }

  update(eqpt: IEqpt): Observable<EntityResponseType> {
    return this.http.put<IEqpt>(this.resourceUrl, eqpt, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEqpt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEqpt[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
