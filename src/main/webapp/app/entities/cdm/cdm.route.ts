import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cdm } from 'app/shared/model/cdm.model';
import { CdmService } from './cdm.service';
import { CdmComponent } from './cdm.component';
import { CdmDetailComponent } from './cdm-detail.component';
import { CdmUpdateComponent } from './cdm-update.component';
import { CdmDeletePopupComponent } from './cdm-delete-dialog.component';
import { ICdm } from 'app/shared/model/cdm.model';

@Injectable({ providedIn: 'root' })
export class CdmResolve implements Resolve<ICdm> {
  constructor(private service: CdmService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICdm> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cdm>) => response.ok),
        map((cdm: HttpResponse<Cdm>) => cdm.body)
      );
    }
    return of(new Cdm());
  }
}

export const cdmRoute: Routes = [
  {
    path: '',
    component: CdmComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.cdm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CdmDetailComponent,
    resolve: {
      cdm: CdmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.cdm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CdmUpdateComponent,
    resolve: {
      cdm: CdmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.cdm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CdmUpdateComponent,
    resolve: {
      cdm: CdmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.cdm.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cdmPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CdmDeletePopupComponent,
    resolve: {
      cdm: CdmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.cdm.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
