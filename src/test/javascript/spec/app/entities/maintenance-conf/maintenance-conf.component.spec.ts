import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { MaintenanceConfComponent } from 'app/entities/maintenance-conf/maintenance-conf.component';
import { MaintenanceConfService } from 'app/entities/maintenance-conf/maintenance-conf.service';
import { MaintenanceConf } from 'app/shared/model/maintenance-conf.model';

describe('Component Tests', () => {
  describe('MaintenanceConf Management Component', () => {
    let comp: MaintenanceConfComponent;
    let fixture: ComponentFixture<MaintenanceConfComponent>;
    let service: MaintenanceConfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [MaintenanceConfComponent],
        providers: []
      })
        .overrideTemplate(MaintenanceConfComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MaintenanceConfComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaintenanceConfService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MaintenanceConf(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.maintenanceConfs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
