import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';

type EntityResponseType = HttpResponse<IMaintenanceConfCombination>;
type EntityArrayResponseType = HttpResponse<IMaintenanceConfCombination[]>;

@Injectable({ providedIn: 'root' })
export class MaintenanceConfCombinationService {
  public resourceUrl = SERVER_API_URL + 'api/maintenance-conf-combinations';

  constructor(protected http: HttpClient) {}

  create(maintenanceConfCombination: IMaintenanceConfCombination): Observable<EntityResponseType> {
    return this.http.post<IMaintenanceConfCombination>(this.resourceUrl, maintenanceConfCombination, { observe: 'response' });
  }

  update(maintenanceConfCombination: IMaintenanceConfCombination): Observable<EntityResponseType> {
    return this.http.put<IMaintenanceConfCombination>(this.resourceUrl, maintenanceConfCombination, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMaintenanceConfCombination>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaintenanceConfCombination[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
