import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { IMaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';

export interface IMaintenanceConf {
  id?: number;
  name?: string;
  dicoCDM?: IDicoCDM;
  maintenanceConfCombination?: IMaintenanceConfCombination;
}

export class MaintenanceConf implements IMaintenanceConf {
  constructor(
    public id?: number,
    public name?: string,
    public dicoCDM?: IDicoCDM,
    public maintenanceConfCombination?: IMaintenanceConfCombination
  ) {}
}
