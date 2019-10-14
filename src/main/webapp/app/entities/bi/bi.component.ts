import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IBi } from 'app/shared/model/bi.model';
import { AccountService } from 'app/core/auth/account.service';
import { BiService } from './bi.service';

@Component({
  selector: 'jhi-bi',
  templateUrl: './bi.component.html'
})
export class BiComponent implements OnInit, OnDestroy {
  bis: IBi[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected biService: BiService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.biService
      .query()
      .pipe(
        filter((res: HttpResponse<IBi[]>) => res.ok),
        map((res: HttpResponse<IBi[]>) => res.body)
      )
      .subscribe((res: IBi[]) => {
        this.bis = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBis();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBi) {
    return item.id;
  }

  registerChangeInBis() {
    this.eventSubscriber = this.eventManager.subscribe('biListModification', response => this.loadAll());
  }
}
