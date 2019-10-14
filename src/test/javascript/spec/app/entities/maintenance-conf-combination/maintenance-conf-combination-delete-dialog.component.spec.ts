import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestmdcTestModule } from '../../../test.module';
import { MaintenanceConfCombinationDeleteDialogComponent } from 'app/entities/maintenance-conf-combination/maintenance-conf-combination-delete-dialog.component';
import { MaintenanceConfCombinationService } from 'app/entities/maintenance-conf-combination/maintenance-conf-combination.service';

describe('Component Tests', () => {
  describe('MaintenanceConfCombination Management Delete Component', () => {
    let comp: MaintenanceConfCombinationDeleteDialogComponent;
    let fixture: ComponentFixture<MaintenanceConfCombinationDeleteDialogComponent>;
    let service: MaintenanceConfCombinationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [MaintenanceConfCombinationDeleteDialogComponent]
      })
        .overrideTemplate(MaintenanceConfCombinationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaintenanceConfCombinationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaintenanceConfCombinationService);
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
