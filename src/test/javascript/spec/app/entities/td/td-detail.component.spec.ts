import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { TdDetailComponent } from 'app/entities/td/td-detail.component';
import { Td } from 'app/shared/model/td.model';

describe('Component Tests', () => {
  describe('Td Management Detail Component', () => {
    let comp: TdDetailComponent;
    let fixture: ComponentFixture<TdDetailComponent>;
    const route = ({ data: of({ td: new Td(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [TdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.td).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
