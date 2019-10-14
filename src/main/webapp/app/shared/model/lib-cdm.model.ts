import { ICdm } from 'app/shared/model/cdm.model';

export interface ILibCdm {
  id?: number;
  nameFR?: string;
  nameGB?: string;
  commentFR?: string;
  commentGB?: string;
  docNameFR?: string;
  docNameGB?: string;
  cdm?: ICdm;
}

export class LibCdm implements ILibCdm {
  constructor(
    public id?: number,
    public nameFR?: string,
    public nameGB?: string,
    public commentFR?: string,
    public commentGB?: string,
    public docNameFR?: string,
    public docNameGB?: string,
    public cdm?: ICdm
  ) {}
}
