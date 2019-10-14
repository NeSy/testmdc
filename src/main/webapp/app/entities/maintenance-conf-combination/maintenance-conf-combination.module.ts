import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { MaintenanceConfCombinationComponent } from './maintenance-conf-combination.component';
import { MaintenanceConfCombinationDetailComponent } from './maintenance-conf-combination-detail.component';
import { MaintenanceConfCombinationUpdateComponent } from './maintenance-conf-combination-update.component';
import {
  MaintenanceConfCombinationDeletePopupComponent,
  MaintenanceConfCombinationDeleteDialogComponent
} from './maintenance-conf-combination-delete-dialog.component';
import { maintenanceConfCombinationRoute, maintenanceConfCombinationPopupRoute } from './maintenance-conf-combination.route';

const ENTITY_STATES = [...maintenanceConfCombinationRoute, ...maintenanceConfCombinationPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MaintenanceConfCombinationComponent,
    MaintenanceConfCombinationDetailComponent,
    MaintenanceConfCombinationUpdateComponent,
    MaintenanceConfCombinationDeleteDialogComponent,
    MaintenanceConfCombinationDeletePopupComponent
  ],
  entryComponents: [MaintenanceConfCombinationDeleteDialogComponent]
})
export class TestmdcMaintenanceConfCombinationModule {}
