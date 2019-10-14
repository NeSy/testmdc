import { IEqpt } from 'app/shared/model/eqpt.model';
import { ITd } from 'app/shared/model/td.model';

export interface IBi {
  id?: number;
  nameFR?: string;
  nameGB?: string;
  commentFR?: string;
  commentGB?: string;
  mnemonicFR?: string;
  mnemonicGB?: string;
  cdp?: string;
  eqpt?: IEqpt;
  tds?: ITd[];
}

export class Bi implements IBi {
  constructor(
    public id?: number,
    public nameFR?: string,
    public nameGB?: string,
    public commentFR?: string,
    public commentGB?: string,
    public mnemonicFR?: string,
    public mnemonicGB?: string,
    public cdp?: string,
    public eqpt?: IEqpt,
    public tds?: ITd[]
  ) {}
}
