import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { TdComponent } from './td.component';
import { TdDetailComponent } from './td-detail.component';
import { TdUpdateComponent } from './td-update.component';
import { TdDeletePopupComponent, TdDeleteDialogComponent } from './td-delete-dialog.component';
import { tdRoute, tdPopupRoute } from './td.route';

const ENTITY_STATES = [...tdRoute, ...tdPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TdComponent, TdDetailComponent, TdUpdateComponent, TdDeleteDialogComponent, TdDeletePopupComponent],
  entryComponents: [TdDeleteDialogComponent]
})
export class TestmdcTdModule {}
