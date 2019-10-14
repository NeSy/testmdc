import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AircraftConf } from 'app/shared/model/aircraft-conf.model';
import { AircraftConfService } from './aircraft-conf.service';
import { AircraftConfComponent } from './aircraft-conf.component';
import { AircraftConfDetailComponent } from './aircraft-conf-detail.component';
import { AircraftConfUpdateComponent } from './aircraft-conf-update.component';
import { AircraftConfDeletePopupComponent } from './aircraft-conf-delete-dialog.component';
import { IAircraftConf } from 'app/shared/model/aircraft-conf.model';

@Injectable({ providedIn: 'root' })
export class AircraftConfResolve implements Resolve<IAircraftConf> {
  constructor(private service: AircraftConfService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAircraftConf> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AircraftConf>) => response.ok),
        map((aircraftConf: HttpResponse<AircraftConf>) => aircraftConf.body)
      );
    }
    return of(new AircraftConf());
  }
}

export const aircraftConfRoute: Routes = [
  {
    path: '',
    component: AircraftConfComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.aircraftConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AircraftConfDetailComponent,
    resolve: {
      aircraftConf: AircraftConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.aircraftConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AircraftConfUpdateComponent,
    resolve: {
      aircraftConf: AircraftConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.aircraftConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AircraftConfUpdateComponent,
    resolve: {
      aircraftConf: AircraftConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.aircraftConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const aircraftConfPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AircraftConfDeletePopupComponent,
    resolve: {
      aircraftConf: AircraftConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.aircraftConf.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
