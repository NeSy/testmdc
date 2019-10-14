import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDicoCDM, DicoCDM } from 'app/shared/model/dico-cdm.model';
import { DicoCDMService } from './dico-cdm.service';

@Component({
  selector: 'jhi-dico-cdm-update',
  templateUrl: './dico-cdm-update.component.html'
})
export class DicoCDMUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    prevDicoId: [],
    imtRef: [],
    version: [null, [Validators.required]],
    date: [null, [Validators.required]],
    releaseDate: [],
    toolVersion: [],
    state: [null, [Validators.required]]
  });

  constructor(protected dicoCDMService: DicoCDMService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dicoCDM }) => {
      this.updateForm(dicoCDM);
    });
  }

  updateForm(dicoCDM: IDicoCDM) {
    this.editForm.patchValue({
      id: dicoCDM.id,
      name: dicoCDM.name,
      prevDicoId: dicoCDM.prevDicoId,
      imtRef: dicoCDM.imtRef,
      version: dicoCDM.version,
      date: dicoCDM.date,
      releaseDate: dicoCDM.releaseDate,
      toolVersion: dicoCDM.toolVersion,
      state: dicoCDM.state
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dicoCDM = this.createFromForm();
    if (dicoCDM.id !== undefined) {
      this.subscribeToSaveResponse(this.dicoCDMService.update(dicoCDM));
    } else {
      this.subscribeToSaveResponse(this.dicoCDMService.create(dicoCDM));
    }
  }

  private createFromForm(): IDicoCDM {
    return {
      ...new DicoCDM(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      prevDicoId: this.editForm.get(['prevDicoId']).value,
      imtRef: this.editForm.get(['imtRef']).value,
      version: this.editForm.get(['version']).value,
      date: this.editForm.get(['date']).value,
      releaseDate: this.editForm.get(['releaseDate']).value,
      toolVersion: this.editForm.get(['toolVersion']).value,
      state: this.editForm.get(['state']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDicoCDM>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
