import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { EqptUpdateComponent } from 'app/entities/eqpt/eqpt-update.component';
import { EqptService } from 'app/entities/eqpt/eqpt.service';
import { Eqpt } from 'app/shared/model/eqpt.model';

describe('Component Tests', () => {
  describe('Eqpt Management Update Component', () => {
    let comp: EqptUpdateComponent;
    let fixture: ComponentFixture<EqptUpdateComponent>;
    let service: EqptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [EqptUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EqptUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EqptUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EqptService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Eqpt(123);
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
        const entity = new Eqpt();
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
