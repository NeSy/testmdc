import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Td } from 'app/shared/model/td.model';
import { TdService } from './td.service';
import { TdComponent } from './td.component';
import { TdDetailComponent } from './td-detail.component';
import { TdUpdateComponent } from './td-update.component';
import { TdDeletePopupComponent } from './td-delete-dialog.component';
import { ITd } from 'app/shared/model/td.model';

@Injectable({ providedIn: 'root' })
export class TdResolve implements Resolve<ITd> {
  constructor(private service: TdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITd> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Td>) => response.ok),
        map((td: HttpResponse<Td>) => td.body)
      );
    }
    return of(new Td());
  }
}

export const tdRoute: Routes = [
  {
    path: '',
    component: TdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.td.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TdDetailComponent,
    resolve: {
      td: TdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.td.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TdUpdateComponent,
    resolve: {
      td: TdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.td.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TdUpdateComponent,
    resolve: {
      td: TdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.td.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TdDeletePopupComponent,
    resolve: {
      td: TdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.td.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
