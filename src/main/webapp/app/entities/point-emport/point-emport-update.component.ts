import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPointEmport, PointEmport } from 'app/shared/model/point-emport.model';
import { PointEmportService } from './point-emport.service';
import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { DicoCDMService } from 'app/entities/dico-cdm/dico-cdm.service';

@Component({
  selector: 'jhi-point-emport-update',
  templateUrl: './point-emport-update.component.html'
})
export class PointEmportUpdateComponent implements OnInit {
  isSaving: boolean;

  dicocdms: IDicoCDM[];

  editForm = this.fb.group({
    id: [],
    nameFR: [null, [Validators.required]],
    nameGB: [],
    commentFR: [null, [Validators.required]],
    commentGB: [],
    mnemonicGB: [],
    dicoCDM: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pointEmportService: PointEmportService,
    protected dicoCDMService: DicoCDMService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pointEmport }) => {
      this.updateForm(pointEmport);
    });
    this.dicoCDMService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDicoCDM[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDicoCDM[]>) => response.body)
      )
      .subscribe((res: IDicoCDM[]) => (this.dicocdms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pointEmport: IPointEmport) {
    this.editForm.patchValue({
      id: pointEmport.id,
      nameFR: pointEmport.nameFR,
      nameGB: pointEmport.nameGB,
      commentFR: pointEmport.commentFR,
      commentGB: pointEmport.commentGB,
      mnemonicGB: pointEmport.mnemonicGB,
      dicoCDM: pointEmport.dicoCDM
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pointEmport = this.createFromForm();
    if (pointEmport.id !== undefined) {
      this.subscribeToSaveResponse(this.pointEmportService.update(pointEmport));
    } else {
      this.subscribeToSaveResponse(this.pointEmportService.create(pointEmport));
    }
  }

  private createFromForm(): IPointEmport {
    return {
      ...new PointEmport(),
      id: this.editForm.get(['id']).value,
      nameFR: this.editForm.get(['nameFR']).value,
      nameGB: this.editForm.get(['nameGB']).value,
      commentFR: this.editForm.get(['commentFR']).value,
      commentGB: this.editForm.get(['commentGB']).value,
      mnemonicGB: this.editForm.get(['mnemonicGB']).value,
      dicoCDM: this.editForm.get(['dicoCDM']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointEmport>>) {
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
