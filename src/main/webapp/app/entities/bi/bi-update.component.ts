import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBi, Bi } from 'app/shared/model/bi.model';
import { BiService } from './bi.service';
import { IEqpt } from 'app/shared/model/eqpt.model';
import { EqptService } from 'app/entities/eqpt/eqpt.service';

@Component({
  selector: 'jhi-bi-update',
  templateUrl: './bi-update.component.html'
})
export class BiUpdateComponent implements OnInit {
  isSaving: boolean;

  eqpts: IEqpt[];

  editForm = this.fb.group({
    id: [],
    nameFR: [null, [Validators.required]],
    nameGB: [],
    commentFR: [null, [Validators.required]],
    commentGB: [],
    mnemonicFR: [null, [Validators.required]],
    mnemonicGB: [],
    cdp: [],
    eqpt: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected biService: BiService,
    protected eqptService: EqptService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bi }) => {
      this.updateForm(bi);
    });
    this.eqptService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEqpt[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEqpt[]>) => response.body)
      )
      .subscribe((res: IEqpt[]) => (this.eqpts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(bi: IBi) {
    this.editForm.patchValue({
      id: bi.id,
      nameFR: bi.nameFR,
      nameGB: bi.nameGB,
      commentFR: bi.commentFR,
      commentGB: bi.commentGB,
      mnemonicFR: bi.mnemonicFR,
      mnemonicGB: bi.mnemonicGB,
      cdp: bi.cdp,
      eqpt: bi.eqpt
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bi = this.createFromForm();
    if (bi.id !== undefined) {
      this.subscribeToSaveResponse(this.biService.update(bi));
    } else {
      this.subscribeToSaveResponse(this.biService.create(bi));
    }
  }

  private createFromForm(): IBi {
    return {
      ...new Bi(),
      id: this.editForm.get(['id']).value,
      nameFR: this.editForm.get(['nameFR']).value,
      nameGB: this.editForm.get(['nameGB']).value,
      commentFR: this.editForm.get(['commentFR']).value,
      commentGB: this.editForm.get(['commentGB']).value,
      mnemonicFR: this.editForm.get(['mnemonicFR']).value,
      mnemonicGB: this.editForm.get(['mnemonicGB']).value,
      cdp: this.editForm.get(['cdp']).value,
      eqpt: this.editForm.get(['eqpt']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBi>>) {
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
}
