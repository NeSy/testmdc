import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPointEmport } from 'app/shared/model/point-emport.model';
import { AccountService } from 'app/core/auth/account.service';
import { PointEmportService } from './point-emport.service';

@Component({
  selector: 'jhi-point-emport',
  templateUrl: './point-emport.component.html'
})
export class PointEmportComponent implements OnInit, OnDestroy {
  pointEmports: IPointEmport[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pointEmportService: PointEmportService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pointEmportService
      .query()
      .pipe(
        filter((res: HttpResponse<IPointEmport[]>) => res.ok),
        map((res: HttpResponse<IPointEmport[]>) => res.body)
      )
      .subscribe((res: IPointEmport[]) => {
        this.pointEmports = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPointEmports();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPointEmport) {
    return item.id;
  }

  registerChangeInPointEmports() {
    this.eventSubscriber = this.eventManager.subscribe('pointEmportListModification', response => this.loadAll());
  }
}
