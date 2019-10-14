import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { PointEmportComponent } from './point-emport.component';
import { PointEmportDetailComponent } from './point-emport-detail.component';
import { PointEmportUpdateComponent } from './point-emport-update.component';
import { PointEmportDeletePopupComponent, PointEmportDeleteDialogComponent } from './point-emport-delete-dialog.component';
import { pointEmportRoute, pointEmportPopupRoute } from './point-emport.route';

const ENTITY_STATES = [...pointEmportRoute, ...pointEmportPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PointEmportComponent,
    PointEmportDetailComponent,
    PointEmportUpdateComponent,
    PointEmportDeleteDialogComponent,
    PointEmportDeletePopupComponent
  ],
  entryComponents: [PointEmportDeleteDialogComponent]
})
export class TestmdcPointEmportModule {}
