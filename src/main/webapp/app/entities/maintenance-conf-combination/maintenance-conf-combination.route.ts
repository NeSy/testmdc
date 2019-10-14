import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';
import { MaintenanceConfCombinationService } from './maintenance-conf-combination.service';
import { MaintenanceConfCombinationComponent } from './maintenance-conf-combination.component';
import { MaintenanceConfCombinationDetailComponent } from './maintenance-conf-combination-detail.component';
import { MaintenanceConfCombinationUpdateComponent } from './maintenance-conf-combination-update.component';
import { MaintenanceConfCombinationDeletePopupComponent } from './maintenance-conf-combination-delete-dialog.component';
import { IMaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';

@Injectable({ providedIn: 'root' })
export class MaintenanceConfCombinationResolve implements Resolve<IMaintenanceConfCombination> {
  constructor(private service: MaintenanceConfCombinationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMaintenanceConfCombination> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MaintenanceConfCombination>) => response.ok),
        map((maintenanceConfCombination: HttpResponse<MaintenanceConfCombination>) => maintenanceConfCombination.body)
      );
    }
    return of(new MaintenanceConfCombination());
  }
}

export const maintenanceConfCombinationRoute: Routes = [
  {
    path: '',
    component: MaintenanceConfCombinationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConfCombination.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MaintenanceConfCombinationDetailComponent,
    resolve: {
      maintenanceConfCombination: MaintenanceConfCombinationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConfCombination.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MaintenanceConfCombinationUpdateComponent,
    resolve: {
      maintenanceConfCombination: MaintenanceConfCombinationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConfCombination.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MaintenanceConfCombinationUpdateComponent,
    resolve: {
      maintenanceConfCombination: MaintenanceConfCombinationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConfCombination.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const maintenanceConfCombinationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MaintenanceConfCombinationDeletePopupComponent,
    resolve: {
      maintenanceConfCombination: MaintenanceConfCombinationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.maintenanceConfCombination.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
