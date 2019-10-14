import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestmdcTestModule } from '../../../test.module';
import { LibCdmDeleteDialogComponent } from 'app/entities/lib-cdm/lib-cdm-delete-dialog.component';
import { LibCdmService } from 'app/entities/lib-cdm/lib-cdm.service';

describe('Component Tests', () => {
  describe('LibCdm Management Delete Component', () => {
    let comp: LibCdmDeleteDialogComponent;
    let fixture: ComponentFixture<LibCdmDeleteDialogComponent>;
    let service: LibCdmService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [LibCdmDeleteDialogComponent]
      })
        .overrideTemplate(LibCdmDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LibCdmDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LibCdmService);
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
