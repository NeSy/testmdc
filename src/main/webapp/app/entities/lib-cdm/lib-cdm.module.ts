import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { LibCdmComponent } from './lib-cdm.component';
import { LibCdmDetailComponent } from './lib-cdm-detail.component';
import { LibCdmUpdateComponent } from './lib-cdm-update.component';
import { LibCdmDeletePopupComponent, LibCdmDeleteDialogComponent } from './lib-cdm-delete-dialog.component';
import { libCdmRoute, libCdmPopupRoute } from './lib-cdm.route';

const ENTITY_STATES = [...libCdmRoute, ...libCdmPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [LibCdmComponent, LibCdmDetailComponent, LibCdmUpdateComponent, LibCdmDeleteDialogComponent, LibCdmDeletePopupComponent],
  entryComponents: [LibCdmDeleteDialogComponent]
})
export class TestmdcLibCdmModule {}
