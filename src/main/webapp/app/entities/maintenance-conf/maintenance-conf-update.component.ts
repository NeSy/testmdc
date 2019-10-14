import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMaintenanceConf, MaintenanceConf } from 'app/shared/model/maintenance-conf.model';
import { MaintenanceConfService } from './maintenance-conf.service';
import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { DicoCDMService } from 'app/entities/dico-cdm/dico-cdm.service';
import { IMaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';
import { MaintenanceConfCombinationService } from 'app/entities/maintenance-conf-combination/maintenance-conf-combination.service';

@Component({
  selector: 'jhi-maintenance-conf-update',
  templateUrl: './maintenance-conf-update.component.html'
})
export class MaintenanceConfUpdateComponent implements OnInit {
  isSaving: boolean;

  dicocdms: IDicoCDM[];

  maintenanceconfcombinations: IMaintenanceConfCombination[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    dicoCDM: [],
    maintenanceConfCombination: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected maintenanceConfService: MaintenanceConfService,
    protected dicoCDMService: DicoCDMService,
    protected maintenanceConfCombinationService: MaintenanceConfCombinationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ maintenanceConf }) => {
      this.updateForm(maintenanceConf);
    });
    this.dicoCDMService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDicoCDM[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDicoCDM[]>) => response.body)
      )
      .subscribe((res: IDicoCDM[]) => (this.dicocdms = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.maintenanceConfCombinationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMaintenanceConfCombination[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMaintenanceConfCombination[]>) => response.body)
      )
      .subscribe(
        (res: IMaintenanceConfCombination[]) => (this.maintenanceconfcombinations = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(maintenanceConf: IMaintenanceConf) {
    this.editForm.patchValue({
      id: maintenanceConf.id,
      name: maintenanceConf.name,
      dicoCDM: maintenanceConf.dicoCDM,
      maintenanceConfCombination: maintenanceConf.maintenanceConfCombination
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const maintenanceConf = this.createFromForm();
    if (maintenanceConf.id !== undefined) {
      this.subscribeToSaveResponse(this.maintenanceConfService.update(maintenanceConf));
    } else {
      this.subscribeToSaveResponse(this.maintenanceConfService.create(maintenanceConf));
    }
  }

  private createFromForm(): IMaintenanceConf {
    return {
      ...new MaintenanceConf(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      dicoCDM: this.editForm.get(['dicoCDM']).value,
      maintenanceConfCombination: this.editForm.get(['maintenanceConfCombination']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaintenanceConf>>) {
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

  trackMaintenanceConfCombinationById(index: number, item: IMaintenanceConfCombination) {
    return item.id;
  }
}
