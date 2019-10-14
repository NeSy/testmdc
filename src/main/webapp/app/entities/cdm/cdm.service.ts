import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICdm } from 'app/shared/model/cdm.model';

type EntityResponseType = HttpResponse<ICdm>;
type EntityArrayResponseType = HttpResponse<ICdm[]>;

@Injectable({ providedIn: 'root' })
export class CdmService {
  public resourceUrl = SERVER_API_URL + 'api/cdms';

  constructor(protected http: HttpClient) {}

  create(cdm: ICdm): Observable<EntityResponseType> {
    return this.http.post<ICdm>(this.resourceUrl, cdm, { observe: 'response' });
  }

  update(cdm: ICdm): Observable<EntityResponseType> {
    return this.http.put<ICdm>(this.resourceUrl, cdm, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICdm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICdm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
