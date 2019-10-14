import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPointEmport } from 'app/shared/model/point-emport.model';

@Component({
  selector: 'jhi-point-emport-detail',
  templateUrl: './point-emport-detail.component.html'
})
export class PointEmportDetailComponent implements OnInit {
  pointEmport: IPointEmport;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pointEmport }) => {
      this.pointEmport = pointEmport;
    });
  }

  previousState() {
    window.history.back();
  }
}
