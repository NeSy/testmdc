import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { CdmComponent } from 'app/entities/cdm/cdm.component';
import { CdmService } from 'app/entities/cdm/cdm.service';
import { Cdm } from 'app/shared/model/cdm.model';

describe('Component Tests', () => {
  describe('Cdm Management Component', () => {
    let comp: CdmComponent;
    let fixture: ComponentFixture<CdmComponent>;
    let service: CdmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [CdmComponent],
        providers: []
      })
        .overrideTemplate(CdmComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CdmComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CdmService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Cdm(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cdms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
