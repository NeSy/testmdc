import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { PointEmportDetailComponent } from 'app/entities/point-emport/point-emport-detail.component';
import { PointEmport } from 'app/shared/model/point-emport.model';

describe('Component Tests', () => {
  describe('PointEmport Management Detail Component', () => {
    let comp: PointEmportDetailComponent;
    let fixture: ComponentFixture<PointEmportDetailComponent>;
    const route = ({ data: of({ pointEmport: new PointEmport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [PointEmportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PointEmportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PointEmportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pointEmport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
