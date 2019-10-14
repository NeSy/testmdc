import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestmdcSharedModule } from 'app/shared/shared.module';
import { BusComponent } from './bus.component';
import { BusDetailComponent } from './bus-detail.component';
import { BusUpdateComponent } from './bus-update.component';
import { BusDeletePopupComponent, BusDeleteDialogComponent } from './bus-delete-dialog.component';
import { busRoute, busPopupRoute } from './bus.route';

const ENTITY_STATES = [...busRoute, ...busPopupRoute];

@NgModule({
  imports: [TestmdcSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [BusComponent, BusDetailComponent, BusUpdateComponent, BusDeleteDialogComponent, BusDeletePopupComponent],
  entryComponents: [BusDeleteDialogComponent]
})
export class TestmdcBusModule {}
