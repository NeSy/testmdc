import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMaintenanceConf } from 'app/shared/model/maintenance-conf.model';

@Component({
  selector: 'jhi-maintenance-conf-detail',
  templateUrl: './maintenance-conf-detail.component.html'
})
export class MaintenanceConfDetailComponent implements OnInit {
  maintenanceConf: IMaintenanceConf;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ maintenanceConf }) => {
      this.maintenanceConf = maintenanceConf;
    });
  }

  previousState() {
    window.history.back();
  }
}
