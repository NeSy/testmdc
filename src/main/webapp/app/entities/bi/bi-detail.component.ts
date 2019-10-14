import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBi } from 'app/shared/model/bi.model';

@Component({
  selector: 'jhi-bi-detail',
  templateUrl: './bi-detail.component.html'
})
export class BiDetailComponent implements OnInit {
  bi: IBi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bi }) => {
      this.bi = bi;
    });
  }

  previousState() {
    window.history.back();
  }
}
