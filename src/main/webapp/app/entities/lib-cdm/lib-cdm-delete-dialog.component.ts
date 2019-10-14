import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILibCdm } from 'app/shared/model/lib-cdm.model';
import { LibCdmService } from './lib-cdm.service';

@Component({
  selector: 'jhi-lib-cdm-delete-dialog',
  templateUrl: './lib-cdm-delete-dialog.component.html'
})
export class LibCdmDeleteDialogComponent {
  libCdm: ILibCdm;

  constructor(protected libCdmService: LibCdmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.libCdmService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'libCdmListModification',
        content: 'Deleted an libCdm'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-lib-cdm-delete-popup',
  template: ''
})
export class LibCdmDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ libCdm }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LibCdmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.libCdm = libCdm;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/lib-cdm', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/lib-cdm', { outlets: { popup: null } }]);
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
