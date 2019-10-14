import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { CdmDetailComponent } from 'app/entities/cdm/cdm-detail.component';
import { Cdm } from 'app/shared/model/cdm.model';

describe('Component Tests', () => {
  describe('Cdm Management Detail Component', () => {
    let comp: CdmDetailComponent;
    let fixture: ComponentFixture<CdmDetailComponent>;
    const route = ({ data: of({ cdm: new Cdm(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [CdmDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CdmDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CdmDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cdm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
