import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { LibCdmComponent } from 'app/entities/lib-cdm/lib-cdm.component';
import { LibCdmService } from 'app/entities/lib-cdm/lib-cdm.service';
import { LibCdm } from 'app/shared/model/lib-cdm.model';

describe('Component Tests', () => {
  describe('LibCdm Management Component', () => {
    let comp: LibCdmComponent;
    let fixture: ComponentFixture<LibCdmComponent>;
    let service: LibCdmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [LibCdmComponent],
        providers: []
      })
        .overrideTemplate(LibCdmComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LibCdmComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LibCdmService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LibCdm(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.libCdms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
