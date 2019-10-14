import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILibCdm, LibCdm } from 'app/shared/model/lib-cdm.model';
import { LibCdmService } from './lib-cdm.service';
import { ICdm } from 'app/shared/model/cdm.model';
import { CdmService } from 'app/entities/cdm/cdm.service';

@Component({
  selector: 'jhi-lib-cdm-update',
  templateUrl: './lib-cdm-update.component.html'
})
export class LibCdmUpdateComponent implements OnInit {
  isSaving: boolean;

  cdms: ICdm[];

  editForm = this.fb.group({
    id: [],
    nameFR: [null, [Validators.required]],
    nameGB: [],
    commentFR: [null, [Validators.required]],
    commentGB: [],
    docNameFR: [null, [Validators.required]],
    docNameGB: [],
    cdm: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected libCdmService: LibCdmService,
    protected cdmService: CdmService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ libCdm }) => {
      this.updateForm(libCdm);
    });
    this.cdmService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICdm[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICdm[]>) => response.body)
      )
      .subscribe((res: ICdm[]) => (this.cdms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(libCdm: ILibCdm) {
    this.editForm.patchValue({
      id: libCdm.id,
      nameFR: libCdm.nameFR,
      nameGB: libCdm.nameGB,
      commentFR: libCdm.commentFR,
      commentGB: libCdm.commentGB,
      docNameFR: libCdm.docNameFR,
      docNameGB: libCdm.docNameGB,
      cdm: libCdm.cdm
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const libCdm = this.createFromForm();
    if (libCdm.id !== undefined) {
      this.subscribeToSaveResponse(this.libCdmService.update(libCdm));
    } else {
      this.subscribeToSaveResponse(this.libCdmService.create(libCdm));
    }
  }

  private createFromForm(): ILibCdm {
    return {
      ...new LibCdm(),
      id: this.editForm.get(['id']).value,
      nameFR: this.editForm.get(['nameFR']).value,
      nameGB: this.editForm.get(['nameGB']).value,
      commentFR: this.editForm.get(['commentFR']).value,
      commentGB: this.editForm.get(['commentGB']).value,
      docNameFR: this.editForm.get(['docNameFR']).value,
      docNameGB: this.editForm.get(['docNameGB']).value,
      cdm: this.editForm.get(['cdm']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILibCdm>>) {
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

  trackCdmById(index: number, item: ICdm) {
    return item.id;
  }
}
