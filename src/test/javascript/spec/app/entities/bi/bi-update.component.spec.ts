import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { BiUpdateComponent } from 'app/entities/bi/bi-update.component';
import { BiService } from 'app/entities/bi/bi.service';
import { Bi } from 'app/shared/model/bi.model';

describe('Component Tests', () => {
  describe('Bi Management Update Component', () => {
    let comp: BiUpdateComponent;
    let fixture: ComponentFixture<BiUpdateComponent>;
    let service: BiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [BiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bi(123);
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
        const entity = new Bi();
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
