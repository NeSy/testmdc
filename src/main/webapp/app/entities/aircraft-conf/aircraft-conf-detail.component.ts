import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAircraftConf } from 'app/shared/model/aircraft-conf.model';

@Component({
  selector: 'jhi-aircraft-conf-detail',
  templateUrl: './aircraft-conf-detail.component.html'
})
export class AircraftConfDetailComponent implements OnInit {
  aircraftConf: IAircraftConf;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aircraftConf }) => {
      this.aircraftConf = aircraftConf;
    });
  }

  previousState() {
    window.history.back();
  }
}
