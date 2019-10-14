import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestmdcTestModule } from '../../../test.module';
import { PointEmportDeleteDialogComponent } from 'app/entities/point-emport/point-emport-delete-dialog.component';
import { PointEmportService } from 'app/entities/point-emport/point-emport.service';

describe('Component Tests', () => {
  describe('PointEmport Management Delete Component', () => {
    let comp: PointEmportDeleteDialogComponent;
    let fixture: ComponentFixture<PointEmportDeleteDialogComponent>;
    let service: PointEmportService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [PointEmportDeleteDialogComponent]
      })
        .overrideTemplate(PointEmportDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PointEmportDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PointEmportService);
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
