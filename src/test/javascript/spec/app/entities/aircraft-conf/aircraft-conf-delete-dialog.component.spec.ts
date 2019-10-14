import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestmdcTestModule } from '../../../test.module';
import { AircraftConfDeleteDialogComponent } from 'app/entities/aircraft-conf/aircraft-conf-delete-dialog.component';
import { AircraftConfService } from 'app/entities/aircraft-conf/aircraft-conf.service';

describe('Component Tests', () => {
  describe('AircraftConf Management Delete Component', () => {
    let comp: AircraftConfDeleteDialogComponent;
    let fixture: ComponentFixture<AircraftConfDeleteDialogComponent>;
    let service: AircraftConfService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [AircraftConfDeleteDialogComponent]
      })
        .overrideTemplate(AircraftConfDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AircraftConfDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AircraftConfService);
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
