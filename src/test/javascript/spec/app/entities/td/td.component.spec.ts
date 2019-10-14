import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { TdComponent } from 'app/entities/td/td.component';
import { TdService } from 'app/entities/td/td.service';
import { Td } from 'app/shared/model/td.model';

describe('Component Tests', () => {
  describe('Td Management Component', () => {
    let comp: TdComponent;
    let fixture: ComponentFixture<TdComponent>;
    let service: TdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [TdComponent],
        providers: []
      })
        .overrideTemplate(TdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Td(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
