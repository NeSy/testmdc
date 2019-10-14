import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PointEmport } from 'app/shared/model/point-emport.model';
import { PointEmportService } from './point-emport.service';
import { PointEmportComponent } from './point-emport.component';
import { PointEmportDetailComponent } from './point-emport-detail.component';
import { PointEmportUpdateComponent } from './point-emport-update.component';
import { PointEmportDeletePopupComponent } from './point-emport-delete-dialog.component';
import { IPointEmport } from 'app/shared/model/point-emport.model';

@Injectable({ providedIn: 'root' })
export class PointEmportResolve implements Resolve<IPointEmport> {
  constructor(private service: PointEmportService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPointEmport> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PointEmport>) => response.ok),
        map((pointEmport: HttpResponse<PointEmport>) => pointEmport.body)
      );
    }
    return of(new PointEmport());
  }
}

export const pointEmportRoute: Routes = [
  {
    path: '',
    component: PointEmportComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.pointEmport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PointEmportDetailComponent,
    resolve: {
      pointEmport: PointEmportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.pointEmport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PointEmportUpdateComponent,
    resolve: {
      pointEmport: PointEmportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.pointEmport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PointEmportUpdateComponent,
    resolve: {
      pointEmport: PointEmportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.pointEmport.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pointEmportPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PointEmportDeletePopupComponent,
    resolve: {
      pointEmport: PointEmportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.pointEmport.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
