import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDicoCDM } from 'app/shared/model/dico-cdm.model';

type EntityResponseType = HttpResponse<IDicoCDM>;
type EntityArrayResponseType = HttpResponse<IDicoCDM[]>;

@Injectable({ providedIn: 'root' })
export class DicoCDMService {
  public resourceUrl = SERVER_API_URL + 'api/dico-cdms';

  constructor(protected http: HttpClient) {}

  create(dicoCDM: IDicoCDM): Observable<EntityResponseType> {
    return this.http.post<IDicoCDM>(this.resourceUrl, dicoCDM, { observe: 'response' });
  }

  update(dicoCDM: IDicoCDM): Observable<EntityResponseType> {
    return this.http.put<IDicoCDM>(this.resourceUrl, dicoCDM, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDicoCDM>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDicoCDM[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
