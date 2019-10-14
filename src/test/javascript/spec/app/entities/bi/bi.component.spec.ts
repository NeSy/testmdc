import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { BiComponent } from 'app/entities/bi/bi.component';
import { BiService } from 'app/entities/bi/bi.service';
import { Bi } from 'app/shared/model/bi.model';

describe('Component Tests', () => {
  describe('Bi Management Component', () => {
    let comp: BiComponent;
    let fixture: ComponentFixture<BiComponent>;
    let service: BiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [BiComponent],
        providers: []
      })
        .overrideTemplate(BiComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Bi(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bis[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
