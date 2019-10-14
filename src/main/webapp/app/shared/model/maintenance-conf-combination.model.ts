import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { IMaintenanceConf } from 'app/shared/model/maintenance-conf.model';

export interface IMaintenanceConfCombination {
  id?: number;
  name?: string;
  dicoCDM?: IDicoCDM;
  maintenanceConfRefs?: IMaintenanceConf[];
}

export class MaintenanceConfCombination implements IMaintenanceConfCombination {
  constructor(public id?: number, public name?: string, public dicoCDM?: IDicoCDM, public maintenanceConfRefs?: IMaintenanceConf[]) {}
}
