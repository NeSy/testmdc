import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { MaintenanceConfDetailComponent } from 'app/entities/maintenance-conf/maintenance-conf-detail.component';
import { MaintenanceConf } from 'app/shared/model/maintenance-conf.model';

describe('Component Tests', () => {
  describe('MaintenanceConf Management Detail Component', () => {
    let comp: MaintenanceConfDetailComponent;
    let fixture: ComponentFixture<MaintenanceConfDetailComponent>;
    const route = ({ data: of({ maintenanceConf: new MaintenanceConf(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [MaintenanceConfDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MaintenanceConfDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaintenanceConfDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.maintenanceConf).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
