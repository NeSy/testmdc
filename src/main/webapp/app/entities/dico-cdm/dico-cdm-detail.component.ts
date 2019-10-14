import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDicoCDM } from 'app/shared/model/dico-cdm.model';

@Component({
  selector: 'jhi-dico-cdm-detail',
  templateUrl: './dico-cdm-detail.component.html'
})
export class DicoCDMDetailComponent implements OnInit {
  dicoCDM: IDicoCDM;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dicoCDM }) => {
      this.dicoCDM = dicoCDM;
    });
  }

  previousState() {
    window.history.back();
  }
}
