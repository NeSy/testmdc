import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { PointEmportComponent } from 'app/entities/point-emport/point-emport.component';
import { PointEmportService } from 'app/entities/point-emport/point-emport.service';
import { PointEmport } from 'app/shared/model/point-emport.model';

describe('Component Tests', () => {
  describe('PointEmport Management Component', () => {
    let comp: PointEmportComponent;
    let fixture: ComponentFixture<PointEmportComponent>;
    let service: PointEmportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [PointEmportComponent],
        providers: []
      })
        .overrideTemplate(PointEmportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PointEmportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PointEmportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PointEmport(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pointEmports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
