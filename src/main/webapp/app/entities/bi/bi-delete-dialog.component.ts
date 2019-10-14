import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBi } from 'app/shared/model/bi.model';
import { BiService } from './bi.service';

@Component({
  selector: 'jhi-bi-delete-dialog',
  templateUrl: './bi-delete-dialog.component.html'
})
export class BiDeleteDialogComponent {
  bi: IBi;

  constructor(protected biService: BiService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.biService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'biListModification',
        content: 'Deleted an bi'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-bi-delete-popup',
  template: ''
})
export class BiDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bi }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BiDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bi = bi;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/bi', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/bi', { outlets: { popup: null } }]);
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
