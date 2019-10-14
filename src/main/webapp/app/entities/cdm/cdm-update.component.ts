import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICdm, Cdm } from 'app/shared/model/cdm.model';
import { CdmService } from './cdm.service';
import { IEqpt } from 'app/shared/model/eqpt.model';
import { EqptService } from 'app/entities/eqpt/eqpt.service';
import { IMaintenanceConf } from 'app/shared/model/maintenance-conf.model';
import { MaintenanceConfService } from 'app/entities/maintenance-conf/maintenance-conf.service';

@Component({
  selector: 'jhi-cdm-update',
  templateUrl: './cdm-update.component.html'
})
export class CdmUpdateComponent implements OnInit {
  isSaving: boolean;

  eqpts: IEqpt[];

  maintenanceconfigurations: IMaintenanceConf[];

  editForm = this.fb.group({
    id: [],
    index: [null, [Validators.required, Validators.pattern('[0-9][0-9]')]],
    nameFR: [null, [Validators.required]],
    nameGB: [],
    commentFR: [null, [Validators.required]],
    commentGB: [],
    docNameFR: [null, [Validators.required]],
    docNameGB: [],
    busMessage: [null, [Validators.required]],
    busWord: [null, [Validators.required]],
    mnemonicFR: [null, [Validators.required]],
    offset: [],
    coding: [],
    unitMsg: [],
    minMsg: [],
    maxMsg: [],
    nature: [],
    sign: [],
    cadrageVTL: [],
    minValueVTL: [],
    maxValueVTL: [],
    minByteVTL: [],
    maxByteVTL: [],
    unitVTL: [],
    kind: [],
    linear: [null, [Validators.required]],
    func: [null, [Validators.required]],
    eqpt: [],
    maintenanceConfiguration: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cdmService: CdmService,
    protected eqptService: EqptService,
    protected maintenanceConfService: MaintenanceConfService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cdm }) => {
      this.updateForm(cdm);
    });
    this.eqptService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEqpt[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEqpt[]>) => response.body)
      )
      .subscribe((res: IEqpt[]) => (this.eqpts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.maintenanceConfService
      .query({ filter: 'cdm-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IMaintenanceConf[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMaintenanceConf[]>) => response.body)
      )
      .subscribe(
        (res: IMaintenanceConf[]) => {
          if (!this.editForm.get('maintenanceConfiguration').value || !this.editForm.get('maintenanceConfiguration').value.id) {
            this.maintenanceconfigurations = res;
          } else {
            this.maintenanceConfService
              .find(this.editForm.get('maintenanceConfiguration').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IMaintenanceConf>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IMaintenanceConf>) => subResponse.body)
              )
              .subscribe(
                (subRes: IMaintenanceConf) => (this.maintenanceconfigurations = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(cdm: ICdm) {
    this.editForm.patchValue({
      id: cdm.id,
      index: cdm.index,
      nameFR: cdm.nameFR,
      nameGB: cdm.nameGB,
      commentFR: cdm.commentFR,
      commentGB: cdm.commentGB,
      docNameFR: cdm.docNameFR,
      docNameGB: cdm.docNameGB,
      busMessage: cdm.busMessage,
      busWord: cdm.busWord,
      mnemonicFR: cdm.mnemonicFR,
      offset: cdm.offset,
      coding: cdm.coding,
      unitMsg: cdm.unitMsg,
      minMsg: cdm.minMsg,
      maxMsg: cdm.maxMsg,
      nature: cdm.nature,
      sign: cdm.sign,
      cadrageVTL: cdm.cadrageVTL,
      minValueVTL: cdm.minValueVTL,
      maxValueVTL: cdm.maxValueVTL,
      minByteVTL: cdm.minByteVTL,
      maxByteVTL: cdm.maxByteVTL,
      unitVTL: cdm.unitVTL,
      kind: cdm.kind,
      linear: cdm.linear,
      func: cdm.func,
      eqpt: cdm.eqpt,
      maintenanceConfiguration: cdm.maintenanceConfiguration
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cdm = this.createFromForm();
    if (cdm.id !== undefined) {
      this.subscribeToSaveResponse(this.cdmService.update(cdm));
    } else {
      this.subscribeToSaveResponse(this.cdmService.create(cdm));
    }
  }

  private createFromForm(): ICdm {
    return {
      ...new Cdm(),
      id: this.editForm.get(['id']).value,
      index: this.editForm.get(['index']).value,
      nameFR: this.editForm.get(['nameFR']).value,
      nameGB: this.editForm.get(['nameGB']).value,
      commentFR: this.editForm.get(['commentFR']).value,
      commentGB: this.editForm.get(['commentGB']).value,
      docNameFR: this.editForm.get(['docNameFR']).value,
      docNameGB: this.editForm.get(['docNameGB']).value,
      busMessage: this.editForm.get(['busMessage']).value,
      busWord: this.editForm.get(['busWord']).value,
      mnemonicFR: this.editForm.get(['mnemonicFR']).value,
      offset: this.editForm.get(['offset']).value,
      coding: this.editForm.get(['coding']).value,
      unitMsg: this.editForm.get(['unitMsg']).value,
      minMsg: this.editForm.get(['minMsg']).value,
      maxMsg: this.editForm.get(['maxMsg']).value,
      nature: this.editForm.get(['nature']).value,
      sign: this.editForm.get(['sign']).value,
      cadrageVTL: this.editForm.get(['cadrageVTL']).value,
      minValueVTL: this.editForm.get(['minValueVTL']).value,
      maxValueVTL: this.editForm.get(['maxValueVTL']).value,
      minByteVTL: this.editForm.get(['minByteVTL']).value,
      maxByteVTL: this.editForm.get(['maxByteVTL']).value,
      unitVTL: this.editForm.get(['unitVTL']).value,
      kind: this.editForm.get(['kind']).value,
      linear: this.editForm.get(['linear']).value,
      func: this.editForm.get(['func']).value,
      eqpt: this.editForm.get(['eqpt']).value,
      maintenanceConfiguration: this.editForm.get(['maintenanceConfiguration']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICdm>>) {
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

  trackMaintenanceConfById(index: number, item: IMaintenanceConf) {
    return item.id;
  }
}
