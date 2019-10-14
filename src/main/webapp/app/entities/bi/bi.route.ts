import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bi } from 'app/shared/model/bi.model';
import { BiService } from './bi.service';
import { BiComponent } from './bi.component';
import { BiDetailComponent } from './bi-detail.component';
import { BiUpdateComponent } from './bi-update.component';
import { BiDeletePopupComponent } from './bi-delete-dialog.component';
import { IBi } from 'app/shared/model/bi.model';

@Injectable({ providedIn: 'root' })
export class BiResolve implements Resolve<IBi> {
  constructor(private service: BiService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBi> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Bi>) => response.ok),
        map((bi: HttpResponse<Bi>) => bi.body)
      );
    }
    return of(new Bi());
  }
}

export const biRoute: Routes = [
  {
    path: '',
    component: BiComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bi.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BiDetailComponent,
    resolve: {
      bi: BiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bi.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BiUpdateComponent,
    resolve: {
      bi: BiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bi.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BiUpdateComponent,
    resolve: {
      bi: BiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bi.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const biPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BiDeletePopupComponent,
    resolve: {
      bi: BiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bi.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
