import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestmdcTestModule } from '../../../test.module';
import { LibCdmDetailComponent } from 'app/entities/lib-cdm/lib-cdm-detail.component';
import { LibCdm } from 'app/shared/model/lib-cdm.model';

describe('Component Tests', () => {
  describe('LibCdm Management Detail Component', () => {
    let comp: LibCdmDetailComponent;
    let fixture: ComponentFixture<LibCdmDetailComponent>;
    const route = ({ data: of({ libCdm: new LibCdm(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [LibCdmDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LibCdmDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LibCdmDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.libCdm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
