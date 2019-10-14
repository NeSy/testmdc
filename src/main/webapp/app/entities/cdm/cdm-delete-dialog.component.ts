import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICdm } from 'app/shared/model/cdm.model';
import { CdmService } from './cdm.service';

@Component({
  selector: 'jhi-cdm-delete-dialog',
  templateUrl: './cdm-delete-dialog.component.html'
})
export class CdmDeleteDialogComponent {
  cdm: ICdm;

  constructor(protected cdmService: CdmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cdmService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cdmListModification',
        content: 'Deleted an cdm'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cdm-delete-popup',
  template: ''
})
export class CdmDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cdm }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CdmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cdm = cdm;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cdm', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cdm', { outlets: { popup: null } }]);
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
