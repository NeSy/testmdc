import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { MaintenanceConfComponent } from './maintenance-conf.component';
import { MaintenanceConfDetailComponent } from './maintenance-conf-detail.component';
import { MaintenanceConfUpdateComponent } from './maintenance-conf-update.component';
import { MaintenanceConfDeletePopupComponent, MaintenanceConfDeleteDialogComponent } from './maintenance-conf-delete-dialog.component';
import { maintenanceConfRoute, maintenanceConfPopupRoute } from './maintenance-conf.route';

const ENTITY_STATES = [...maintenanceConfRoute, ...maintenanceConfPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MaintenanceConfComponent,
    MaintenanceConfDetailComponent,
    MaintenanceConfUpdateComponent,
    MaintenanceConfDeleteDialogComponent,
    MaintenanceConfDeletePopupComponent
  ],
  entryComponents: [MaintenanceConfDeleteDialogComponent]
})
export class TestmdcMaintenanceConfModule {}
