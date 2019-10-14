import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { PointEmportUpdateComponent } from 'app/entities/point-emport/point-emport-update.component';
import { PointEmportService } from 'app/entities/point-emport/point-emport.service';
import { PointEmport } from 'app/shared/model/point-emport.model';

describe('Component Tests', () => {
  describe('PointEmport Management Update Component', () => {
    let comp: PointEmportUpdateComponent;
    let fixture: ComponentFixture<PointEmportUpdateComponent>;
    let service: PointEmportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [PointEmportUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PointEmportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PointEmportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PointEmportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PointEmport(123);
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
        const entity = new PointEmport();
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
