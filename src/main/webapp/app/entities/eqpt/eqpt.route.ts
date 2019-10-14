import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Eqpt } from 'app/shared/model/eqpt.model';
import { EqptService } from './eqpt.service';
import { EqptComponent } from './eqpt.component';
import { EqptDetailComponent } from './eqpt-detail.component';
import { EqptUpdateComponent } from './eqpt-update.component';
import { EqptDeletePopupComponent } from './eqpt-delete-dialog.component';
import { IEqpt } from 'app/shared/model/eqpt.model';

@Injectable({ providedIn: 'root' })
export class EqptResolve implements Resolve<IEqpt> {
  constructor(private service: EqptService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEqpt> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Eqpt>) => response.ok),
        map((eqpt: HttpResponse<Eqpt>) => eqpt.body)
      );
    }
    return of(new Eqpt());
  }
}

export const eqptRoute: Routes = [
  {
    path: '',
    component: EqptComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.eqpt.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EqptDetailComponent,
    resolve: {
      eqpt: EqptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.eqpt.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EqptUpdateComponent,
    resolve: {
      eqpt: EqptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.eqpt.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EqptUpdateComponent,
    resolve: {
      eqpt: EqptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.eqpt.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const eqptPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EqptDeletePopupComponent,
    resolve: {
      eqpt: EqptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.eqpt.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
