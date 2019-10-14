import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { BiComponent } from './bi.component';
import { BiDetailComponent } from './bi-detail.component';
import { BiUpdateComponent } from './bi-update.component';
import { BiDeletePopupComponent, BiDeleteDialogComponent } from './bi-delete-dialog.component';
import { biRoute, biPopupRoute } from './bi.route';

const ENTITY_STATES = [...biRoute, ...biPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [BiComponent, BiDetailComponent, BiUpdateComponent, BiDeleteDialogComponent, BiDeletePopupComponent],
  entryComponents: [BiDeleteDialogComponent]
})
export class TestmdcBiModule {}
