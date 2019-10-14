import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITd } from 'app/shared/model/td.model';
import { TdService } from './td.service';

@Component({
  selector: 'jhi-td-delete-dialog',
  templateUrl: './td-delete-dialog.component.html'
})
export class TdDeleteDialogComponent {
  td: ITd;

  constructor(protected tdService: TdService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tdListModification',
        content: 'Deleted an td'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-td-delete-popup',
  template: ''
})
export class TdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ td }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.td = td;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/td', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/td', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
