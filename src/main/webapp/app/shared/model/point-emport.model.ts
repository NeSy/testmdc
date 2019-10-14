import { IDicoCDM } from 'app/shared/model/dico-cdm.model';
import { IEqpt } from 'app/shared/model/eqpt.model';

export interface IPointEmport {
  id?: number;
  nameFR?: string;
  nameGB?: string;
  commentFR?: string;
  commentGB?: string;
  mnemonicGB?: string;
  dicoCDM?: IDicoCDM;
  eqpts?: IEqpt[];
}

export class PointEmport implements IPointEmport {
  constructor(
    public id?: number,
    public nameFR?: string,
    public nameGB?: string,
    public commentFR?: string,
    public commentGB?: string,
    public mnemonicGB?: string,
    public dicoCDM?: IDicoCDM,
    public eqpts?: IEqpt[]
  ) {}
}
