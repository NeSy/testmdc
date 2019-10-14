import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAircraftConf, AircraftConf } from 'app/shared/model/aircraft-conf.model';
import { AircraftConfService } from './aircraft-conf.service';
import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { DicoCDMService } from 'app/entities/dico-cdm/dico-cdm.service';
import { ICdm } from 'app/shared/model/cdm.model';
import { CdmService } from 'app/entities/cdm/cdm.service';

@Component({
  selector: 'jhi-aircraft-conf-update',
  templateUrl: './aircraft-conf-update.component.html'
})
export class AircraftConfUpdateComponent implements OnInit {
  isSaving: boolean;

  dicocdms: IDicoCDM[];

  cdms: ICdm[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    dicoCDM: [],
    cdm: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected aircraftConfService: AircraftConfService,
    protected dicoCDMService: DicoCDMService,
    protected cdmService: CdmService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aircraftConf }) => {
      this.updateForm(aircraftConf);
    });
    this.dicoCDMService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDicoCDM[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDicoCDM[]>) => response.body)
      )
      .subscribe((res: IDicoCDM[]) => (this.dicocdms = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.cdmService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICdm[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICdm[]>) => response.body)
      )
      .subscribe((res: ICdm[]) => (this.cdms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(aircraftConf: IAircraftConf) {
    this.editForm.patchValue({
      id: aircraftConf.id,
      name: aircraftConf.name,
      dicoCDM: aircraftConf.dicoCDM,
      cdm: aircraftConf.cdm
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const aircraftConf = this.createFromForm();
    if (aircraftConf.id !== undefined) {
      this.subscribeToSaveResponse(this.aircraftConfService.update(aircraftConf));
    } else {
      this.subscribeToSaveResponse(this.aircraftConfService.create(aircraftConf));
    }
  }

  private createFromForm(): IAircraftConf {
    return {
      ...new AircraftConf(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      dicoCDM: this.editForm.get(['dicoCDM']).value,
      cdm: this.editForm.get(['cdm']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAircraftConf>>) {
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

  trackCdmById(index: number, item: ICdm) {
    return item.id;
  }
}
