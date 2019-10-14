import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dico-cdm',
        loadChildren: () => import('./dico-cdm/dico-cdm.module').then(m => m.TestmdcDicoCDMModule)
      },
      {
        path: 'point-emport',
        loadChildren: () => import('./point-emport/point-emport.module').then(m => m.TestmdcPointEmportModule)
      },
      {
        path: 'eqpt',
        loadChildren: () => import('./eqpt/eqpt.module').then(m => m.TestmdcEqptModule)
      },
      {
        path: 'cdm',
        loadChildren: () => import('./cdm/cdm.module').then(m => m.TestmdcCdmModule)
      },
      {
        path: 'lib-cdm',
        loadChildren: () => import('./lib-cdm/lib-cdm.module').then(m => m.TestmdcLibCdmModule)
      },
      {
        path: 'bi',
        loadChildren: () => import('./bi/bi.module').then(m => m.TestmdcBiModule)
      },
      {
        path: 'td',
        loadChildren: () => import('./td/td.module').then(m => m.TestmdcTdModule)
      },
      {
        path: 'bus',
        loadChildren: () => import('./bus/bus.module').then(m => m.TestmdcBusModule)
      },
      {
        path: 'aircraft-conf',
        loadChildren: () => import('./aircraft-conf/aircraft-conf.module').then(m => m.TestmdcAircraftConfModule)
      },
      {
        path: 'maintenance-conf',
        loadChildren: () => import('./maintenance-conf/maintenance-conf.module').then(m => m.TestmdcMaintenanceConfModule)
      },
      {
        path: 'maintenance-conf-combination',
        loadChildren: () =>
          import('./maintenance-conf-combination/maintenance-conf-combination.module').then(m => m.TestmdcMaintenanceConfCombinationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TestmdcEntityModule {}
