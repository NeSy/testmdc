import { IPointEmport } from 'app/shared/model/point-emport.model';
import { IEqpt } from 'app/shared/model/eqpt.model';
import { IAircraftConf } from 'app/shared/model/aircraft-conf.model';
import { IMaintenanceConf } from 'app/shared/model/maintenance-conf.model';
import { IMaintenanceConfCombination } from 'app/shared/model/maintenance-conf-combination.model';

export interface IDicoCDM {
  id?: number;
  name?: string;
  prevDicoId?: string;
  imtRef?: string;
  version?: string;
  date?: string;
  releaseDate?: string;
  toolVersion?: string;
  state?: string;
  pointEmports?: IPointEmport[];
  sas?: IEqpt[];
  aircraftConfs?: IAircraftConf[];
  maintenanceConfs?: IMaintenanceConf[];
  maintenaceConfCombinations?: IMaintenanceConfCombination[];
}

export class DicoCDM implements IDicoCDM {
  constructor(
    public id?: number,
    public name?: string,
    public prevDicoId?: string,
    public imtRef?: string,
    public version?: string,
    public date?: string,
    public releaseDate?: string,
    public toolVersion?: string,
    public state?: string,
    public pointEmports?: IPointEmport[],
    public sas?: IEqpt[],
    public aircraftConfs?: IAircraftConf[],
    public maintenanceConfs?: IMaintenanceConf[],
    public maintenaceConfCombinations?: IMaintenanceConfCombination[]
  ) {}
}
