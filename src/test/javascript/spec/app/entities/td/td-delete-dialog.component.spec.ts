import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestmdcTestModule } from '../../../test.module';
import { TdDeleteDialogComponent } from 'app/entities/td/td-delete-dialog.component';
import { TdService } from 'app/entities/td/td.service';

describe('Component Tests', () => {
  describe('Td Management Delete Component', () => {
    let comp: TdDeleteDialogComponent;
    let fixture: ComponentFixture<TdDeleteDialogComponent>;
    let service: TdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestmdcTestModule],
        declarations: [TdDeleteDialogComponent]
      })
        .overrideTemplate(TdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TdService);
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
