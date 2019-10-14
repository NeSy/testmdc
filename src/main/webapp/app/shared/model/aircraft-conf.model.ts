import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { ICdm } from 'app/shared/model/cdm.model';

export interface IAircraftConf {
  id?: number;
  name?: string;
  dicoCDM?: IDicoCDM;
  cdm?: ICdm;
}

export class AircraftConf implements IAircraftConf {
  constructor(public id?: number, public name?: string, public dicoCDM?: IDicoCDM, public cdm?: ICdm) {}
}
