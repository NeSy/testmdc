import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEqpt } from 'app/shared/model/eqpt.model';

@Component({
  selector: 'jhi-eqpt-detail',
  templateUrl: './eqpt-detail.component.html'
})
export class EqptDetailComponent implements OnInit {
  eqpt: IEqpt;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eqpt }) => {
      this.eqpt = eqpt;
    });
  }

  previousState() {
    window.history.back();
  }
}
