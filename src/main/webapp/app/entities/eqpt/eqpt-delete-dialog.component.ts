import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEqpt } from 'app/shared/model/eqpt.model';
import { EqptService } from './eqpt.service';

@Component({
  selector: 'jhi-eqpt-delete-dialog',
  templateUrl: './eqpt-delete-dialog.component.html'
})
export class EqptDeleteDialogComponent {
  eqpt: IEqpt;

  constructor(protected eqptService: EqptService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.eqptService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'eqptListModification',
        content: 'Deleted an eqpt'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-eqpt-delete-popup',
  template: ''
})
export class EqptDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eqpt }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EqptDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.eqpt = eqpt;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/eqpt', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/eqpt', { outlets: { popup: null } }]);
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
