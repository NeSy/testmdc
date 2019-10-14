import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { AccountService } from 'app/core/auth/account.service';
import { DicoCDMService } from './dico-cdm.service';

@Component({
  selector: 'jhi-dico-cdm',
  templateUrl: './dico-cdm.component.html'
})
export class DicoCDMComponent implements OnInit, OnDestroy {
  dicoCDMS: IDicoCDM[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dicoCDMService: DicoCDMService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dicoCDMService
      .query()
      .pipe(
        filter((res: HttpResponse<IDicoCDM[]>) => res.ok),
        map((res: HttpResponse<IDicoCDM[]>) => res.body)
      )
      .subscribe((res: IDicoCDM[]) => {
        this.dicoCDMS = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDicoCDMS();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDicoCDM) {
    return item.id;
  }

  registerChangeInDicoCDMS() {
    this.eventSubscriber = this.eventManager.subscribe('dicoCDMListModification', response => this.loadAll());
  }
}
