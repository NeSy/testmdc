import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { MaintenanceConfCombinationDetailComponent } from 'app/entities/maintenance-conf-combination/maintenance-conf-combination-detail.component';
import { MaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';

describe('Component Tests', () => {
  describe('MaintenanceConfCombination Management Detail Component', () => {
    let comp: MaintenanceConfCombinationDetailComponent;
    let fixture: ComponentFixture<MaintenanceConfCombinationDetailComponent>;
    const route = ({ data: of({ maintenanceConfCombination: new MaintenanceConfCombination(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [MaintenanceConfCombinationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MaintenanceConfCombinationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaintenanceConfCombinationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.maintenanceConfCombination).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
