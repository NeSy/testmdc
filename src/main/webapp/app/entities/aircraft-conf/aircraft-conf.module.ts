import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { AircraftConfComponent } from './aircraft-conf.component';
import { AircraftConfDetailComponent } from './aircraft-conf-detail.component';
import { AircraftConfUpdateComponent } from './aircraft-conf-update.component';
import { AircraftConfDeletePopupComponent, AircraftConfDeleteDialogComponent } from './aircraft-conf-delete-dialog.component';
import { aircraftConfRoute, aircraftConfPopupRoute } from './aircraft-conf.route';

const ENTITY_STATES = [...aircraftConfRoute, ...aircraftConfPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AircraftConfComponent,
    AircraftConfDetailComponent,
    AircraftConfUpdateComponent,
    AircraftConfDeleteDialogComponent,
    AircraftConfDeletePopupComponent
  ],
  entryComponents: [AircraftConfDeleteDialogComponent]
})
export class TestmdcAircraftConfModule {}
