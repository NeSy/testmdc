import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';
import { MaintenanceConfCombinationService } from './maintenance-conf-combination.service';

@Component({
  selector: 'jhi-maintenance-conf-combination-delete-dialog',
  templateUrl: './maintenance-conf-combination-delete-dialog.component.html'
})
export class MaintenanceConfCombinationDeleteDialogComponent {
  maintenanceConfCombination: IMaintenanceConfCombination;

  constructor(
    protected maintenanceConfCombinationService: MaintenanceConfCombinationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.maintenanceConfCombinationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'maintenanceConfCombinationListModification',
        content: 'Deleted an maintenanceConfCombination'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-maintenance-conf-combination-delete-popup',
  template: ''
})
export class MaintenanceConfCombinationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ maintenanceConfCombination }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MaintenanceConfCombinationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.maintenanceConfCombination = maintenanceConfCombination;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/maintenance-conf-combination', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/maintenance-conf-combination', { outlets: { popup: null } }]);
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
