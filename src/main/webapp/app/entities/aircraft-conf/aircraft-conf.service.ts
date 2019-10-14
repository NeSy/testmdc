import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAircraftConf } from 'app/shared/model/aircraft-conf.model';

type EntityResponseType = HttpResponse<IAircraftConf>;
type EntityArrayResponseType = HttpResponse<IAircraftConf[]>;

@Injectable({ providedIn: 'root' })
export class AircraftConfService {
  public resourceUrl = SERVER_API_URL + 'api/aircraft-confs';

  constructor(protected http: HttpClient) {}

  create(aircraftConf: IAircraftConf): Observable<EntityResponseType> {
    return this.http.post<IAircraftConf>(this.resourceUrl, aircraftConf, { observe: 'response' });
  }

  update(aircraftConf: IAircraftConf): Observable<EntityResponseType> {
    return this.http.put<IAircraftConf>(this.resourceUrl, aircraftConf, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAircraftConf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAircraftConf[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
