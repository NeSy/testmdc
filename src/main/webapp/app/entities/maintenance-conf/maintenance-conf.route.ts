import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MaintenanceConf } from 'app/shared/model/maintenance-conf.model';
import { MaintenanceConfService } from './maintenance-conf.service';
import { MaintenanceConfComponent } from './maintenance-conf.component';
import { MaintenanceConfDetailComponent } from './maintenance-conf-detail.component';
import { MaintenanceConfUpdateComponent } from './maintenance-conf-update.component';
import { MaintenanceConfDeletePopupComponent } from './maintenance-conf-delete-dialog.component';
import { IMaintenanceConf } from 'app/shared/model/maintenance-conf.model';

@Injectable({ providedIn: 'root' })
export class MaintenanceConfResolve implements Resolve<IMaintenanceConf> {
  constructor(private service: MaintenanceConfService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMaintenanceConf> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MaintenanceConf>) => response.ok),
        map((maintenanceConf: HttpResponse<MaintenanceConf>) => maintenanceConf.body)
      );
    }
    return of(new MaintenanceConf());
  }
}

export const maintenanceConfRoute: Routes = [
  {
    path: '',
    component: MaintenanceConfComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MaintenanceConfDetailComponent,
    resolve: {
      maintenanceConf: MaintenanceConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MaintenanceConfUpdateComponent,
    resolve: {
      maintenanceConf: MaintenanceConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MaintenanceConfUpdateComponent,
    resolve: {
      maintenanceConf: MaintenanceConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const maintenanceConfPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MaintenanceConfDeletePopupComponent,
    resolve: {
      maintenanceConf: MaintenanceConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConf.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
