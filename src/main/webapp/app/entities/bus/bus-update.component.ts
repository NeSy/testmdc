import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBus, Bus } from 'app/shared/model/bus.model';
import { BusService } from './bus.service';

@Component({
  selector: 'jhi-bus-update',
  templateUrl: './bus-update.component.html'
})
export class BusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    index: [null, [Validators.required]],
    name: [null, [Validators.required]]
  });

  constructor(protected busService: BusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bus }) => {
      this.updateForm(bus);
    });
  }

  updateForm(bus: IBus) {
    this.editForm.patchValue({
      id: bus.id,
      index: bus.index,
      name: bus.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bus = this.createFromForm();
    if (bus.id !== undefined) {
      this.subscribeToSaveResponse(this.busService.update(bus));
    } else {
      this.subscribeToSaveResponse(this.busService.create(bus));
    }
  }

  private createFromForm(): IBus {
    return {
      ...new Bus(),
      id: this.editForm.get(['id']).value,
      index: this.editForm.get(['index']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBus>>) {
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
