import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestmdcTestModule } from '../../../test.module';
import { DicoCDMComponent } from 'app/entities/dico-cdm/dico-cdm.component';
import { DicoCDMService } from 'app/entities/dico-cdm/dico-cdm.service';
import { DicoCDM } from 'app/shared/model/dico-cdm.model';

describe('Component Tests', () => {
  describe('DicoCDM Management Component', () => {
    let comp: DicoCDMComponent;
    let fixture: ComponentFixture<DicoCDMComponent>;
    let service: DicoCDMService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [DicoCDMComponent],
        providers: []
      })
        .overrideTemplate(DicoCDMComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DicoCDMComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DicoCDMService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DicoCDM(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dicoCDMS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
