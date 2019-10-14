import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { AircraftConfDetailComponent } from 'app/entities/aircraft-conf/aircraft-conf-detail.component';
import { AircraftConf } from 'app/shared/model/aircraft-conf.model';

describe('Component Tests', () => {
  describe('AircraftConf Management Detail Component', () => {
    let comp: AircraftConfDetailComponent;
    let fixture: ComponentFixture<AircraftConfDetailComponent>;
    const route = ({ data: of({ aircraftConf: new AircraftConf(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [AircraftConfDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AircraftConfDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AircraftConfDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aircraftConf).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
