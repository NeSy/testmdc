import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPointEmport } from 'app/shared/model/point-emport.model';
import { PointEmportService } from './point-emport.service';

@Component({
  selector: 'jhi-point-emport-delete-dialog',
  templateUrl: './point-emport-delete-dialog.component.html'
})
export class PointEmportDeleteDialogComponent {
  pointEmport: IPointEmport;

  constructor(
    protected pointEmportService: PointEmportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pointEmportService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pointEmportListModification',
        content: 'Deleted an pointEmport'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-point-emport-delete-popup',
  template: ''
})
export class PointEmportDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pointEmport }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PointEmportDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pointEmport = pointEmport;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/point-emport', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/point-emport', { outlets: { popup: null } }]);
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
