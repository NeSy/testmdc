import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { IPointEmport } from 'app/shared/model/point-emport.model';
import { IBus } from 'app/shared/model/bus.model';
import { ICdm } from 'app/shared/model/cdm.model';
import { IBi } from 'app/shared/model/bi.model';
import { ITd } from 'app/shared/model/td.model';
import { EqptType } from 'app/shared/model/enumerations/eqpt-type.model';

export interface IEqpt {
  id?: number;
  type?: EqptType;
  nameFR?: string;
  nameGB?: string;
  mnemonicFR?: string;
  mnemonicGB?: string;
  commentFR?: string;
  commentGB?: string;
  address?: string;
  kind?: string;
  cdp?: string;
  dicoCDM?: IDicoCDM;
  pointEmport?: IPointEmport;
  busRef?: IBus;
  cdms?: ICdm[];
  bis?: IBi[];
  tds?: ITd[];
}

export class Eqpt implements IEqpt {
  constructor(
    public id?: number,
    public type?: EqptType,
    public nameFR?: string,
    public nameGB?: string,
    public mnemonicFR?: string,
    public mnemonicGB?: string,
    public commentFR?: string,
    public commentGB?: string,
    public address?: string,
    public kind?: string,
    public cdp?: string,
    public dicoCDM?: IDicoCDM,
    public pointEmport?: IPointEmport,
    public busRef?: IBus,
    public cdms?: ICdm[],
    public bis?: IBi[],
    public tds?: ITd[]
  ) {}
}
