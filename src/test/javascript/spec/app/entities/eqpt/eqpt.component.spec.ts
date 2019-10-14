import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { EqptComponent } from 'app/entities/eqpt/eqpt.component';
import { EqptService } from 'app/entities/eqpt/eqpt.service';
import { Eqpt } from 'app/shared/model/eqpt.model';

describe('Component Tests', () => {
  describe('Eqpt Management Component', () => {
    let comp: EqptComponent;
    let fixture: ComponentFixture<EqptComponent>;
    let service: EqptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [EqptComponent],
        providers: []
      })
        .overrideTemplate(EqptComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EqptComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EqptService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Eqpt(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eqpts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
