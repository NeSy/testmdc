import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAircraftConf } from 'app/shared/model/aircraft-conf.model';
import { AccountService } from 'app/core/auth/account.service';
import { AircraftConfService } from './aircraft-conf.service';

@Component({
  selector: 'jhi-aircraft-conf',
  templateUrl: './aircraft-conf.component.html'
})
export class AircraftConfComponent implements OnInit, OnDestroy {
  aircraftConfs: IAircraftConf[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected aircraftConfService: AircraftConfService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.aircraftConfService
      .query()
      .pipe(
        filter((res: HttpResponse<IAircraftConf[]>) => res.ok),
        map((res: HttpResponse<IAircraftConf[]>) => res.body)
      )
      .subscribe((res: IAircraftConf[]) => {
        this.aircraftConfs = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAircraftConfs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAircraftConf) {
    return item.id;
  }

  registerChangeInAircraftConfs() {
    this.eventSubscriber = this.eventManager.subscribe('aircraftConfListModification', response => this.loadAll());
  }
}
