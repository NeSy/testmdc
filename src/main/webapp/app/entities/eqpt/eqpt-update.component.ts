import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEqpt, Eqpt } from 'app/shared/model/eqpt.model';
import { EqptService } from './eqpt.service';
import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { DicoCDMService } from 'app/entities/dico-cdm/dico-cdm.service';
import { IPointEmport } from 'app/shared/model/point-emport.model';
import { PointEmportService } from 'app/entities/point-emport/point-emport.service';
import { IBus } from 'app/shared/model/bus.model';
import { BusService } from 'app/entities/bus/bus.service';

@Component({
  selector: 'jhi-eqpt-update',
  templateUrl: './eqpt-update.component.html'
})
export class EqptUpdateComponent implements OnInit {
  isSaving: boolean;

  dicocdms: IDicoCDM[];

  pointemports: IPointEmport[];

  busrefs: IBus[];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    nameFR: [null, [Validators.required]],
    nameGB: [],
    mnemonicFR: [null, [Validators.required]],
    mnemonicGB: [],
    commentFR: [null, [Validators.required]],
    commentGB: [],
    address: [null, [Validators.required, Validators.pattern('[0-9][0-9]')]],
    kind: [null, [Validators.required]],
    cdp: [],
    dicoCDM: [],
    pointEmport: [],
    busRef: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected eqptService: EqptService,
    protected dicoCDMService: DicoCDMService,
    protected pointEmportService: PointEmportService,
    protected busService: BusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ eqpt }) => {
      this.updateForm(eqpt);
    });
    this.dicoCDMService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDicoCDM[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDicoCDM[]>) => response.body)
      )
      .subscribe((res: IDicoCDM[]) => (this.dicocdms = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.pointEmportService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPointEmport[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPointEmport[]>) => response.body)
      )
      .subscribe((res: IPointEmport[]) => (this.pointemports = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.busService
      .query({ filter: 'eqpt-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IBus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBus[]>) => response.body)
      )
      .subscribe(
        (res: IBus[]) => {
          if (!this.editForm.get('busRef').value || !this.editForm.get('busRef').value.id) {
            this.busrefs = res;
          } else {
            this.busService
              .find(this.editForm.get('busRef').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IBus>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IBus>) => subResponse.body)
              )
              .subscribe(
                (subRes: IBus) => (this.busrefs = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(eqpt: IEqpt) {
    this.editForm.patchValue({
      id: eqpt.id,
      type: eqpt.type,
      nameFR: eqpt.nameFR,
      nameGB: eqpt.nameGB,
      mnemonicFR: eqpt.mnemonicFR,
      mnemonicGB: eqpt.mnemonicGB,
      commentFR: eqpt.commentFR,
      commentGB: eqpt.commentGB,
      address: eqpt.address,
      kind: eqpt.kind,
      cdp: eqpt.cdp,
      dicoCDM: eqpt.dicoCDM,
      pointEmport: eqpt.pointEmport,
      busRef: eqpt.busRef
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const eqpt = this.createFromForm();
    if (eqpt.id !== undefined) {
      this.subscribeToSaveResponse(this.eqptService.update(eqpt));
    } else {
      this.subscribeToSaveResponse(this.eqptService.create(eqpt));
    }
  }

  private createFromForm(): IEqpt {
    return {
      ...new Eqpt(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value,
      nameFR: this.editForm.get(['nameFR']).value,
      nameGB: this.editForm.get(['nameGB']).value,
      mnemonicFR: this.editForm.get(['mnemonicFR']).value,
      mnemonicGB: this.editForm.get(['mnemonicGB']).value,
      commentFR: this.editForm.get(['commentFR']).value,
      commentGB: this.editForm.get(['commentGB']).value,
      address: this.editForm.get(['address']).value,
      kind: this.editForm.get(['kind']).value,
      cdp: this.editForm.get(['cdp']).value,
      dicoCDM: this.editForm.get(['dicoCDM']).value,
      pointEmport: this.editForm.get(['pointEmport']).value,
      busRef: this.editForm.get(['busRef']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEqpt>>) {
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

  trackDicoCDMById(index: number, item: IDicoCDM) {
    return item.id;
  }

  trackPointEmportById(index: number, item: IPointEmport) {
    return item.id;
  }

  trackBusById(index: number, item: IBus) {
    return item.id;
  }
}
