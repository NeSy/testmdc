import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { AircraftConfUpdateComponent } from 'app/entities/aircraft-conf/aircraft-conf-update.component';
import { AircraftConfService } from 'app/entities/aircraft-conf/aircraft-conf.service';
import { AircraftConf } from 'app/shared/model/aircraft-conf.model';

describe('Component Tests', () => {
  describe('AircraftConf Management Update Component', () => {
    let comp: AircraftConfUpdateComponent;
    let fixture: ComponentFixture<AircraftConfUpdateComponent>;
    let service: AircraftConfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [AircraftConfUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AircraftConfUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AircraftConfUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AircraftConfService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AircraftConf(123);
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
        const entity = new AircraftConf();
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
