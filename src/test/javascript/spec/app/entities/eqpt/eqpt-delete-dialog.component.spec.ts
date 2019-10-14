import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestmdcTestModule } from '../../../test.module';
import { EqptDeleteDialogComponent } from 'app/entities/eqpt/eqpt-delete-dialog.component';
import { EqptService } from 'app/entities/eqpt/eqpt.service';

describe('Component Tests', () => {
  describe('Eqpt Management Delete Component', () => {
    let comp: EqptDeleteDialogComponent;
    let fixture: ComponentFixture<EqptDeleteDialogComponent>;
    let service: EqptService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [EqptDeleteDialogComponent]
      })
        .overrideTemplate(EqptDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EqptDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EqptService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
