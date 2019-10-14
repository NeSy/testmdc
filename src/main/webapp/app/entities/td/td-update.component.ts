import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITd, Td } from 'app/shared/model/td.model';
import { TdService } from './td.service';
import { IEqpt } from 'app/shared/model/eqpt.model';
import { EqptService } from 'app/entities/eqpt/eqpt.service';
import { IBi } from 'app/shared/model/bi.model';
import { BiService } from 'app/entities/bi/bi.service';

@Component({
  selector: 'jhi-td-update',
  templateUrl: './td-update.component.html'
})
export class TdUpdateComponent implements OnInit {
  isSaving: boolean;

  eqpts: IEqpt[];

  bis: IBi[];

  editForm = this.fb.group({
    id: [],
    nameFR: [null, [Validators.required]],
    nameGB: [],
    commentFR: [null, [Validators.required]],
    commentGB: [],
    number: [null, [Validators.required]],
    eqpt: [],
    bi: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tdService: TdService,
    protected eqptService: EqptService,
    protected biService: BiService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ td }) => {
      this.updateForm(td);
    });
    this.eqptService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEqpt[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEqpt[]>) => response.body)
      )
      .subscribe((res: IEqpt[]) => (this.eqpts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.biService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBi[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBi[]>) => response.body)
      )
      .subscribe((res: IBi[]) => (this.bis = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(td: ITd) {
    this.editForm.patchValue({
      id: td.id,
      nameFR: td.nameFR,
      nameGB: td.nameGB,
      commentFR: td.commentFR,
      commentGB: td.commentGB,
      number: td.number,
      eqpt: td.eqpt,
      bi: td.bi
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const td = this.createFromForm();
    if (td.id !== undefined) {
      this.subscribeToSaveResponse(this.tdService.update(td));
    } else {
      this.subscribeToSaveResponse(this.tdService.create(td));
    }
  }

  private createFromForm(): ITd {
    return {
      ...new Td(),
      id: this.editForm.get(['id']).value,
      nameFR: this.editForm.get(['nameFR']).value,
      nameGB: this.editForm.get(['nameGB']).value,
      commentFR: this.editForm.get(['commentFR']).value,
      commentGB: this.editForm.get(['commentGB']).value,
      number: this.editForm.get(['number']).value,
      eqpt: this.editForm.get(['eqpt']).value,
      bi: this.editForm.get(['bi']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITd>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEqptById(index: number, item: IEqpt) {
    return item.id;
  }

  trackBiById(index: number, item: IBi) {
    return item.id;
  }
}
