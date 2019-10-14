import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { AircraftConfComponent } from 'app/entities/aircraft-conf/aircraft-conf.component';
import { AircraftConfService } from 'app/entities/aircraft-conf/aircraft-conf.service';
import { AircraftConf } from 'app/shared/model/aircraft-conf.model';

describe('Component Tests', () => {
  describe('AircraftConf Management Component', () => {
    let comp: AircraftConfComponent;
    let fixture: ComponentFixture<AircraftConfComponent>;
    let service: AircraftConfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [AircraftConfComponent],
        providers: []
      })
        .overrideTemplate(AircraftConfComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AircraftConfComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AircraftConfService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AircraftConf(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aircraftConfs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
