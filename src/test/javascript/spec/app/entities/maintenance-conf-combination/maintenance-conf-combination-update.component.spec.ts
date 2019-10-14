import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { MaintenanceConfCombinationUpdateComponent } from 'app/entities/maintenance-conf-combination/maintenance-conf-combination-update.component';
import { MaintenanceConfCombinationService } from 'app/entities/maintenance-conf-combination/maintenance-conf-combination.service';
import { MaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';

describe('Component Tests', () => {
  describe('MaintenanceConfCombination Management Update Component', () => {
    let comp: MaintenanceConfCombinationUpdateComponent;
    let fixture: ComponentFixture<MaintenanceConfCombinationUpdateComponent>;
    let service: MaintenanceConfCombinationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [MaintenanceConfCombinationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MaintenanceConfCombinationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MaintenanceConfCombinationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaintenanceConfCombinationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MaintenanceConfCombination(123);
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
        const entity = new MaintenanceConfCombination();
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
