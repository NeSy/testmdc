import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IMaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';
import { AccountService } from 'app/core/auth/account.service';
import { MaintenanceConfCombinationService } from './maintenance-conf-combination.service';

@Component({
  selector: 'jhi-maintenance-conf-combination',
  templateUrl: './maintenance-conf-combination.component.html'
})
export class MaintenanceConfCombinationComponent implements OnInit, OnDestroy {
  maintenanceConfCombinations: IMaintenanceConfCombination[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected maintenanceConfCombinationService: MaintenanceConfCombinationService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.maintenanceConfCombinationService
      .query()
      .pipe(
        filter((res: HttpResponse<IMaintenanceConfCombination[]>) => res.ok),
        map((res: HttpResponse<IMaintenanceConfCombination[]>) => res.body)
      )
      .subscribe((res: IMaintenanceConfCombination[]) => {
        this.maintenanceConfCombinations = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMaintenanceConfCombinations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMaintenanceConfCombination) {
    return item.id;
  }

  registerChangeInMaintenanceConfCombinations() {
    this.eventSubscriber = this.eventManager.subscribe('maintenanceConfCombinationListModification', response => this.loadAll());
  }
}
