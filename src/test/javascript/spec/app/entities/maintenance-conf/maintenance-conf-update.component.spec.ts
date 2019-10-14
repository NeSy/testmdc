import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { MaintenanceConfUpdateComponent } from 'app/entities/maintenance-conf/maintenance-conf-update.component';
import { MaintenanceConfService } from 'app/entities/maintenance-conf/maintenance-conf.service';
import { MaintenanceConf } from 'app/shared/model/maintenance-conf.model';

describe('Component Tests', () => {
  describe('MaintenanceConf Management Update Component', () => {
    let comp: MaintenanceConfUpdateComponent;
    let fixture: ComponentFixture<MaintenanceConfUpdateComponent>;
    let service: MaintenanceConfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [MaintenanceConfUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MaintenanceConfUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MaintenanceConfUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaintenanceConfService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MaintenanceConf(123);
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
        const entity = new MaintenanceConf();
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
