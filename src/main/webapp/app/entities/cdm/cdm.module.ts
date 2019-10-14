import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { CdmComponent } from './cdm.component';
import { CdmDetailComponent } from './cdm-detail.component';
import { CdmUpdateComponent } from './cdm-update.component';
import { CdmDeletePopupComponent, CdmDeleteDialogComponent } from './cdm-delete-dialog.component';
import { cdmRoute, cdmPopupRoute } from './cdm.route';

const ENTITY_STATES = [...cdmRoute, ...cdmPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CdmComponent, CdmDetailComponent, CdmUpdateComponent, CdmDeleteDialogComponent, CdmDeletePopupComponent],
  entryComponents: [CdmDeleteDialogComponent]
})
export class TestmdcCdmModule {}
