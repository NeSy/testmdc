import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IMaintenanceConf } from 'app/shared/model/maintenance-conf.model';
import { AccountService } from 'app/core/auth/account.service';
import { MaintenanceConfService } from './maintenance-conf.service';

@Component({
  selector: 'jhi-maintenance-conf',
  templateUrl: './maintenance-conf.component.html'
})
export class MaintenanceConfComponent implements OnInit, OnDestroy {
  maintenanceConfs: IMaintenanceConf[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected maintenanceConfService: MaintenanceConfService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.maintenanceConfService
      .query()
      .pipe(
        filter((res: HttpResponse<IMaintenanceConf[]>) => res.ok),
        map((res: HttpResponse<IMaintenanceConf[]>) => res.body)
      )
      .subscribe((res: IMaintenanceConf[]) => {
        this.maintenanceConfs = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMaintenanceConfs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMaintenanceConf) {
    return item.id;
  }

  registerChangeInMaintenanceConfs() {
    this.eventSubscriber = this.eventManager.subscribe('maintenanceConfListModification', response => this.loadAll());
  }
}
