import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAircraftConf } from 'app/shared/model/aircraft-conf.model';
import { AircraftConfService } from './aircraft-conf.service';

@Component({
  selector: 'jhi-aircraft-conf-delete-dialog',
  templateUrl: './aircraft-conf-delete-dialog.component.html'
})
export class AircraftConfDeleteDialogComponent {
  aircraftConf: IAircraftConf;

  constructor(
    protected aircraftConfService: AircraftConfService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.aircraftConfService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'aircraftConfListModification',
        content: 'Deleted an aircraftConf'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-aircraft-conf-delete-popup',
  template: ''
})
export class AircraftConfDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aircraftConf }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AircraftConfDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.aircraftConf = aircraftConf;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/aircraft-conf', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/aircraft-conf', { outlets: { popup: null } }]);
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
