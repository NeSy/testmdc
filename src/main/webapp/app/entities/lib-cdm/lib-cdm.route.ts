import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LibCdm } from 'app/shared/model/lib-cdm.model';
import { LibCdmService } from './lib-cdm.service';
import { LibCdmComponent } from './lib-cdm.component';
import { LibCdmDetailComponent } from './lib-cdm-detail.component';
import { LibCdmUpdateComponent } from './lib-cdm-update.component';
import { LibCdmDeletePopupComponent } from './lib-cdm-delete-dialog.component';
import { ILibCdm } from 'app/shared/model/lib-cdm.model';

@Injectable({ providedIn: 'root' })
export class LibCdmResolve implements Resolve<ILibCdm> {
  constructor(private service: LibCdmService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILibCdm> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LibCdm>) => response.ok),
        map((libCdm: HttpResponse<LibCdm>) => libCdm.body)
      );
    }
    return of(new LibCdm());
  }
}

export const libCdmRoute: Routes = [
  {
    path: '',
    component: LibCdmComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.libCdm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LibCdmDetailComponent,
    resolve: {
      libCdm: LibCdmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.libCdm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LibCdmUpdateComponent,
    resolve: {
      libCdm: LibCdmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.libCdm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LibCdmUpdateComponent,
    resolve: {
      libCdm: LibCdmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.libCdm.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const libCdmPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LibCdmDeletePopupComponent,
    resolve: {
      libCdm: LibCdmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.libCdm.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
