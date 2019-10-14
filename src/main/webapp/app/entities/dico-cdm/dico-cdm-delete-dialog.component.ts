import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { DicoCDMService } from './dico-cdm.service';

@Component({
  selector: 'jhi-dico-cdm-delete-dialog',
  templateUrl: './dico-cdm-delete-dialog.component.html'
})
export class DicoCDMDeleteDialogComponent {
  dicoCDM: IDicoCDM;

  constructor(protected dicoCDMService: DicoCDMService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dicoCDMService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dicoCDMListModification',
        content: 'Deleted an dicoCDM'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dico-cdm-delete-popup',
  template: ''
})
export class DicoCDMDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dicoCDM }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DicoCDMDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dicoCDM = dicoCDM;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dico-cdm', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dico-cdm', { outlets: { popup: null } }]);
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
