import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { DicoCDMComponent } from './dico-cdm.component';
import { DicoCDMDetailComponent } from './dico-cdm-detail.component';
import { DicoCDMUpdateComponent } from './dico-cdm-update.component';
import { DicoCDMDeletePopupComponent, DicoCDMDeleteDialogComponent } from './dico-cdm-delete-dialog.component';
import { dicoCDMRoute, dicoCDMPopupRoute } from './dico-cdm.route';

const ENTITY_STATES = [...dicoCDMRoute, ...dicoCDMPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DicoCDMComponent,
    DicoCDMDetailComponent,
    DicoCDMUpdateComponent,
    DicoCDMDeleteDialogComponent,
    DicoCDMDeletePopupComponent
  ],
  entryComponents: [DicoCDMDeleteDialogComponent]
})
export class TestmdcDicoCDMModule {}
