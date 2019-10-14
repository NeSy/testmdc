import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';

@Component({
  selector: 'jhi-maintenance-conf-combination-detail',
  templateUrl: './maintenance-conf-combination-detail.component.html'
})
export class MaintenanceConfCombinationDetailComponent implements OnInit {
  maintenanceConfCombination: IMaintenanceConfCombination;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ maintenanceConfCombination }) => {
      this.maintenanceConfCombination = maintenanceConfCombination;
    });
  }

  previousState() {
    window.history.back();
  }
}
