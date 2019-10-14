import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { EqptDetailComponent } from 'app/entities/eqpt/eqpt-detail.component';
import { Eqpt } from 'app/shared/model/eqpt.model';

describe('Component Tests', () => {
  describe('Eqpt Management Detail Component', () => {
    let comp: EqptDetailComponent;
    let fixture: ComponentFixture<EqptDetailComponent>;
    const route = ({ data: of({ eqpt: new Eqpt(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [EqptDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EqptDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EqptDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eqpt).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
