import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICdm } from 'app/shared/model/cdm.model';

@Component({
  selector: 'jhi-cdm-detail',
  templateUrl: './cdm-detail.component.html'
})
export class CdmDetailComponent implements OnInit {
  cdm: ICdm;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cdm }) => {
      this.cdm = cdm;
    });
  }

  previousState() {
    window.history.back();
  }
}
