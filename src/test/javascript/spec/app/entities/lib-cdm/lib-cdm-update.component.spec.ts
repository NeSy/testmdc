import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { LibCdmUpdateComponent } from 'app/entities/lib-cdm/lib-cdm-update.component';
import { LibCdmService } from 'app/entities/lib-cdm/lib-cdm.service';
import { LibCdm } from 'app/shared/model/lib-cdm.model';

describe('Component Tests', () => {
  describe('LibCdm Management Update Component', () => {
    let comp: LibCdmUpdateComponent;
    let fixture: ComponentFixture<LibCdmUpdateComponent>;
    let service: LibCdmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [LibCdmUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LibCdmUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LibCdmUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LibCdmService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LibCdm(123);
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
        const entity = new LibCdm();
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
