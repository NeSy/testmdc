import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPointEmport } from 'app/shared/model/point-emport.model';

type EntityResponseType = HttpResponse<IPointEmport>;
type EntityArrayResponseType = HttpResponse<IPointEmport[]>;

@Injectable({ providedIn: 'root' })
export class PointEmportService {
  public resourceUrl = SERVER_API_URL + 'api/point-emports';

  constructor(protected http: HttpClient) {}

  create(pointEmport: IPointEmport): Observable<EntityResponseType> {
    return this.http.post<IPointEmport>(this.resourceUrl, pointEmport, { observe: 'response' });
  }

  update(pointEmport: IPointEmport): Observable<EntityResponseType> {
    return this.http.put<IPointEmport>(this.resourceUrl, pointEmport, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPointEmport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPointEmport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
