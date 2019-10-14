import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMaintenanceConf } from 'app/shared/model/maintenance-conf.model';
import { MaintenanceConfService } from './maintenance-conf.service';

@Component({
  selector: 'jhi-maintenance-conf-delete-dialog',
  templateUrl: './maintenance-conf-delete-dialog.component.html'
})
export class MaintenanceConfDeleteDialogComponent {
  maintenanceConf: IMaintenanceConf;

  constructor(
    protected maintenanceConfService: MaintenanceConfService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.maintenanceConfService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'maintenanceConfListModification',
        content: 'Deleted an maintenanceConf'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-maintenance-conf-delete-popup',
  template: ''
})
export class MaintenanceConfDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ maintenanceConf }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MaintenanceConfDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.maintenanceConf = maintenanceConf;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/maintenance-conf', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/maintenance-conf', { outlets: { popup: null } }]);
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
