import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITd } from 'app/shared/model/td.model';

@Component({
  selector: 'jhi-td-detail',
  templateUrl: './td-detail.component.html'
})
export class TdDetailComponent implements OnInit {
  td: ITd;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ td }) => {
      this.td = td;
    });
  }

  previousState() {
    window.history.back();
  }
}
