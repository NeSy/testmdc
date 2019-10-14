import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DicoCDM } from 'app/shared/model/dico-cdm.model';
import { DicoCDMService } from './dico-cdm.service';
import { DicoCDMComponent } from './dico-cdm.component';
import { DicoCDMDetailComponent } from './dico-cdm-detail.component';
import { DicoCDMUpdateComponent } from './dico-cdm-update.component';
import { DicoCDMDeletePopupComponent } from './dico-cdm-delete-dialog.component';
import { IDicoCDM } from 'app/shared/model/dico-cdm.model';

@Injectable({ providedIn: 'root' })
export class DicoCDMResolve implements Resolve<IDicoCDM> {
  constructor(private service: DicoCDMService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDicoCDM> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DicoCDM>) => response.ok),
        map((dicoCDM: HttpResponse<DicoCDM>) => dicoCDM.body)
      );
    }
    return of(new DicoCDM());
  }
}

export const dicoCDMRoute: Routes = [
  {
    path: '',
    component: DicoCDMComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.dicoCDM.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DicoCDMDetailComponent,
    resolve: {
      dicoCDM: DicoCDMResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.dicoCDM.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DicoCDMUpdateComponent,
    resolve: {
      dicoCDM: DicoCDMResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.dicoCDM.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DicoCDMUpdateComponent,
    resolve: {
      dicoCDM: DicoCDMResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.dicoCDM.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dicoCDMPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DicoCDMDeletePopupComponent,
    resolve: {
      dicoCDM: DicoCDMResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.dicoCDM.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
