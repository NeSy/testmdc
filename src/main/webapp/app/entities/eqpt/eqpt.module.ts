import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { EqptComponent } from './eqpt.component';
import { EqptDetailComponent } from './eqpt-detail.component';
import { EqptUpdateComponent } from './eqpt-update.component';
import { EqptDeletePopupComponent, EqptDeleteDialogComponent } from './eqpt-delete-dialog.component';
import { eqptRoute, eqptPopupRoute } from './eqpt.route';

const ENTITY_STATES = [...eqptRoute, ...eqptPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [EqptComponent, EqptDetailComponent, EqptUpdateComponent, EqptDeleteDialogComponent, EqptDeletePopupComponent],
  entryComponents: [EqptDeleteDialogComponent]
})
export class TestmdcEqptModule {}
