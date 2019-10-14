import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestmdcTestModule } from '../../../test.module';
import { MaintenanceConfDeleteDialogComponent } from 'app/entities/maintenance-conf/maintenance-conf-delete-dialog.component';
import { MaintenanceConfService } from 'app/entities/maintenance-conf/maintenance-conf.service';

describe('Component Tests', () => {
  describe('MaintenanceConf Management Delete Component', () => {
    let comp: MaintenanceConfDeleteDialogComponent;
    let fixture: ComponentFixture<MaintenanceConfDeleteDialogComponent>;
    let service: MaintenanceConfService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [MaintenanceConfDeleteDialogComponent]
      })
        .overrideTemplate(MaintenanceConfDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaintenanceConfDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaintenanceConfService);
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
