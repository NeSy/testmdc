import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bus } from 'app/shared/model/bus.model';
import { BusService } from './bus.service';
import { BusComponent } from './bus.component';
import { BusDetailComponent } from './bus-detail.component';
import { BusUpdateComponent } from './bus-update.component';
import { BusDeletePopupComponent } from './bus-delete-dialog.component';
import { IBus } from 'app/shared/model/bus.model';

@Injectable({ providedIn: 'root' })
export class BusResolve implements Resolve<IBus> {
  constructor(private service: BusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBus> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Bus>) => response.ok),
        map((bus: HttpResponse<Bus>) => bus.body)
      );
    }
    return of(new Bus());
  }
}

export const busRoute: Routes = [
  {
    path: '',
    component: BusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BusDetailComponent,
    resolve: {
      bus: BusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BusUpdateComponent,
    resolve: {
      bus: BusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BusUpdateComponent,
    resolve: {
      bus: BusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const busPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BusDeletePopupComponent,
    resolve: {
      bus: BusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmdcApp.bus.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
