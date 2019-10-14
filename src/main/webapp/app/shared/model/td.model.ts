import { IEqpt } from 'app/shared/model/eqpt.model';
import { IBi } from 'app/shared/model/bi.model';

export interface ITd {
  id?: number;
  nameFR?: string;
  nameGB?: string;
  commentFR?: string;
  commentGB?: string;
  number?: string;
  eqpt?: IEqpt;
  bi?: IBi;
}

export class Td implements ITd {
  constructor(
    public id?: number,
    public nameFR?: string,
    public nameGB?: string,
    public commentFR?: string,
    public commentGB?: string,
    public number?: string,
    public eqpt?: IEqpt,
    public bi?: IBi
  ) {}
}
