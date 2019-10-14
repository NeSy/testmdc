import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILibCdm } from 'app/shared/model/lib-cdm.model';

@Component({
  selector: 'jhi-lib-cdm-detail',
  templateUrl: './lib-cdm-detail.component.html'
})
export class LibCdmDetailComponent implements OnInit {
  libCdm: ILibCdm;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ libCdm }) => {
      this.libCdm = libCdm;
    });
  }

  previousState() {
    window.history.back();
  }
}
