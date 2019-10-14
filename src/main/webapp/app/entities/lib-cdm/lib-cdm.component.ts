import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ILibCdm } from 'app/shared/model/lib-cdm.model';
import { AccountService } from 'app/core/auth/account.service';
import { LibCdmService } from './lib-cdm.service';

@Component({
  selector: 'jhi-lib-cdm',
  templateUrl: './lib-cdm.component.html'
})
export class LibCdmComponent implements OnInit, OnDestroy {
  libCdms: ILibCdm[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected libCdmService: LibCdmService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.libCdmService
      .query()
      .pipe(
        filter((res: HttpResponse<ILibCdm[]>) => res.ok),
        map((res: HttpResponse<ILibCdm[]>) => res.body)
      )
      .subscribe((res: ILibCdm[]) => {
        this.libCdms = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLibCdms();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILibCdm) {
    return item.id;
  }

  registerChangeInLibCdms() {
    this.eventSubscriber = this.eventManager.subscribe('libCdmListModification', response => this.loadAll());
  }
}
