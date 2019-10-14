import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { MaintenanceConfCombinationComponent } from 'app/entities/maintenance-conf-combination/maintenance-conf-combination.component';
import { MaintenanceConfCombinationService } from 'app/entities/maintenance-conf-combination/maintenance-conf-combination.service';
import { MaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';

describe('Component Tests', () => {
  describe('MaintenanceConfCombination Management Component', () => {
    let comp: MaintenanceConfCombinationComponent;
    let fixture: ComponentFixture<MaintenanceConfCombinationComponent>;
    let service: MaintenanceConfCombinationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [MaintenanceConfCombinationComponent],
        providers: []
      })
        .overrideTemplate(MaintenanceConfCombinationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MaintenanceConfCombinationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaintenanceConfCombinationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MaintenanceConfCombination(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.maintenanceConfCombinations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
