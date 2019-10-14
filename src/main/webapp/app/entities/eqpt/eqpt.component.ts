import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IEqpt } from 'app/shared/model/eqpt.model';
import { AccountService } from 'app/core/auth/account.service';
import { EqptService } from './eqpt.service';

@Component({
  selector: 'jhi-eqpt',
  templateUrl: './eqpt.component.html'
})
export class EqptComponent implements OnInit, OnDestroy {
  eqpts: IEqpt[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected eqptService: EqptService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.eqptService
      .query()
      .pipe(
        filter((res: HttpResponse<IEqpt[]>) => res.ok),
        map((res: HttpResponse<IEqpt[]>) => res.body)
      )
      .subscribe((res: IEqpt[]) => {
        this.eqpts = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEqpts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEqpt) {
    return item.id;
  }

  registerChangeInEqpts() {
    this.eventSubscriber = this.eventManager.subscribe('eqptListModification', response => this.loadAll());
  }
}
