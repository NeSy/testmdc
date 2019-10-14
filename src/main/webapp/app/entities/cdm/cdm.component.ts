import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICdm } from 'app/shared/model/cdm.model';
import { AccountService } from 'app/core/auth/account.service';
import { CdmService } from './cdm.service';

@Component({
  selector: 'jhi-cdm',
  templateUrl: './cdm.component.html'
})
export class CdmComponent implements OnInit, OnDestroy {
  cdms: ICdm[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected cdmService: CdmService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.cdmService
      .query()
      .pipe(
        filter((res: HttpResponse<ICdm[]>) => res.ok),
        map((res: HttpResponse<ICdm[]>) => res.body)
      )
      .subscribe((res: ICdm[]) => {
        this.cdms = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCdms();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICdm) {
    return item.id;
  }

  registerChangeInCdms() {
    this.eventSubscriber = this.eventManager.subscribe('cdmListModification', response => this.loadAll());
  }
}
