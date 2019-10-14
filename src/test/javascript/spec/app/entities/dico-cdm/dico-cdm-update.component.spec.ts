import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { DicoCDMUpdateComponent } from 'app/entities/dico-cdm/dico-cdm-update.component';
import { DicoCDMService } from 'app/entities/dico-cdm/dico-cdm.service';
import { DicoCDM } from 'app/shared/model/dico-cdm.model';

describe('Component Tests', () => {
  describe('DicoCDM Management Update Component', () => {
    let comp: DicoCDMUpdateComponent;
    let fixture: ComponentFixture<DicoCDMUpdateComponent>;
    let service: DicoCDMService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [DicoCDMUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DicoCDMUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DicoCDMUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DicoCDMService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DicoCDM(123);
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
        const entity = new DicoCDM();
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
