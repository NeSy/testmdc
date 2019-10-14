import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { BiDetailComponent } from 'app/entities/bi/bi-detail.component';
import { Bi } from 'app/shared/model/bi.model';

describe('Component Tests', () => {
  describe('Bi Management Detail Component', () => {
    let comp: BiDetailComponent;
    let fixture: ComponentFixture<BiDetailComponent>;
    const route = ({ data: of({ bi: new Bi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [BiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bi).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
