import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMaintenanceConf } from 'app/shared/model/maintenance-conf.model';

type EntityResponseType = HttpResponse<IMaintenanceConf>;
type EntityArrayResponseType = HttpResponse<IMaintenanceConf[]>;

@Injectable({ providedIn: 'root' })
export class MaintenanceConfService {
  public resourceUrl = SERVER_API_URL + 'api/maintenance-confs';

  constructor(protected http: HttpClient) {}

  create(maintenanceConf: IMaintenanceConf): Observable<EntityResponseType> {
    return this.http.post<IMaintenanceConf>(this.resourceUrl, maintenanceConf, { observe: 'response' });
  }

  update(maintenanceConf: IMaintenanceConf): Observable<EntityResponseType> {
    return this.http.put<IMaintenanceConf>(this.resourceUrl, maintenanceConf, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMaintenanceConf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaintenanceConf[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
