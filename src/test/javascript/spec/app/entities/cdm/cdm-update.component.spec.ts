import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { CdmUpdateComponent } from 'app/entities/cdm/cdm-update.component';
import { CdmService } from 'app/entities/cdm/cdm.service';
import { Cdm } from 'app/shared/model/cdm.model';

describe('Component Tests', () => {
  describe('Cdm Management Update Component', () => {
    let comp: CdmUpdateComponent;
    let fixture: ComponentFixture<CdmUpdateComponent>;
    let service: CdmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [CdmUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CdmUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CdmUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CdmService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cdm(123);
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
        const entity = new Cdm();
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
