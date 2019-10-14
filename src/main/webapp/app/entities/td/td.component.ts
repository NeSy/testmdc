import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ITd } from 'app/shared/model/td.model';
import { AccountService } from 'app/core/auth/account.service';
import { TdService } from './td.service';

@Component({
  selector: 'jhi-td',
  templateUrl: './td.component.html'
})
export class TdComponent implements OnInit, OnDestroy {
  tds: ITd[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected tdService: TdService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.tdService
      .query()
      .pipe(
        filter((res: HttpResponse<ITd[]>) => res.ok),
        map((res: HttpResponse<ITd[]>) => res.body)
      )
      .subscribe((res: ITd[]) => {
        this.tds = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITd) {
    return item.id;
  }

  registerChangeInTds() {
    this.eventSubscriber = this.eventManager.subscribe('tdListModification', response => this.loadAll());
  }
}
