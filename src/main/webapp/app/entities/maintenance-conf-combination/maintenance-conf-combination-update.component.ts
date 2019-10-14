import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMaintenanceConfCombination, MaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';
import { MaintenanceConfCombinationService } from './maintenance-conf-combination.service';
import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { DicoCDMService } from 'app/entities/dico-cdm/dico-cdm.service';

@Component({
  selector: 'jhi-maintenance-conf-combination-update',
  templateUrl: './maintenance-conf-combination-update.component.html'
})
export class MaintenanceConfCombinationUpdateComponent implements OnInit {
  isSaving: boolean;

  dicocdms: IDicoCDM[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    dicoCDM: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected maintenanceConfCombinationService: MaintenanceConfCombinationService,
    protected dicoCDMService: DicoCDMService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ maintenanceConfCombination }) => {
      this.updateForm(maintenanceConfCombination);
    });
    this.dicoCDMService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDicoCDM[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDicoCDM[]>) => response.body)
      )
      .subscribe((res: IDicoCDM[]) => (this.dicocdms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(maintenanceConfCombination: IMaintenanceConfCombination) {
    this.editForm.patchValue({
      id: maintenanceConfCombination.id,
      name: maintenanceConfCombination.name,
      dicoCDM: maintenanceConfCombination.dicoCDM
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const maintenanceConfCombination = this.createFromForm();
    if (maintenanceConfCombination.id !== undefined) {
      this.subscribeToSaveResponse(this.maintenanceConfCombinationService.update(maintenanceConfCombination));
    } else {
      this.subscribeToSaveResponse(this.maintenanceConfCombinationService.create(maintenanceConfCombination));
    }
  }

  private createFromForm(): IMaintenanceConfCombination {
    return {
      ...new MaintenanceConfCombination(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      dicoCDM: this.editForm.get(['dicoCDM']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaintenanceConfCombination>>) {
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
}
