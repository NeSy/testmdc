import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { DicoCDMDetailComponent } from 'app/entities/dico-cdm/dico-cdm-detail.component';
import { DicoCDM } from 'app/shared/model/dico-cdm.model';

describe('Component Tests', () => {
  describe('DicoCDM Management Detail Component', () => {
    let comp: DicoCDMDetailComponent;
    let fixture: ComponentFixture<DicoCDMDetailComponent>;
    const route = ({ data: of({ dicoCDM: new DicoCDM(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [DicoCDMDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DicoCDMDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DicoCDMDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dicoCDM).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
