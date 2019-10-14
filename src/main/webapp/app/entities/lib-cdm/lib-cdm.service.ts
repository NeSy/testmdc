import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILibCdm } from 'app/shared/model/lib-cdm.model';

type EntityResponseType = HttpResponse<ILibCdm>;
type EntityArrayResponseType = HttpResponse<ILibCdm[]>;

@Injectable({ providedIn: 'root' })
export class LibCdmService {
  public resourceUrl = SERVER_API_URL + 'api/lib-cdms';

  constructor(protected http: HttpClient) {}

  create(libCdm: ILibCdm): Observable<EntityResponseType> {
    return this.http.post<ILibCdm>(this.resourceUrl, libCdm, { observe: 'response' });
  }

  update(libCdm: ILibCdm): Observable<EntityResponseType> {
    return this.http.put<ILibCdm>(this.resourceUrl, libCdm, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILibCdm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILibCdm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
