import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { TdUpdateComponent } from 'app/entities/td/td-update.component';
import { TdService } from 'app/entities/td/td.service';
import { Td } from 'app/shared/model/td.model';

describe('Component Tests', () => {
  describe('Td Management Update Component', () => {
    let comp: TdUpdateComponent;
    let fixture: ComponentFixture<TdUpdateComponent>;
    let service: TdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [TdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Td(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Td();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
