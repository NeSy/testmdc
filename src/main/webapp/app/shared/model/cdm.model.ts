import { IEqpt } from 'app/shared/model/eqpt.model';
import { IMaintenanceConf } from 'app/shared/model/maintenance-conf.model';
import { ILibCdm } from 'app/shared/model/lib-cdm.model';
import { IAircraftConf } from 'app/shared/model/aircraft-conf.model';
import { CdmNature } from 'app/shared/model/enumerations/cdm-nature.model';
import { EqptKind } from 'app/shared/model/enumerations/eqpt-kind.model';

export interface ICdm {
  id?: number;
  index?: string;
  nameFR?: string;
  nameGB?: string;
  commentFR?: string;
  commentGB?: string;
  docNameFR?: string;
  docNameGB?: string;
  busMessage?: string;
  busWord?: string;
  mnemonicFR?: string;
  offset?: number;
  coding?: string;
  unitMsg?: string;
  minMsg?: string;
  maxMsg?: string;
  nature?: CdmNature;
  sign?: boolean;
  cadrageVTL?: string;
  minValueVTL?: string;
  maxValueVTL?: string;
  minByteVTL?: number;
  maxByteVTL?: number;
  unitVTL?: string;
  kind?: EqptKind;
  linear?: boolean;
  func?: number;
  eqpt?: IEqpt;
  maintenanceConfiguration?: IMaintenanceConf;
  libCdms?: ILibCdm[];
  aircraftConfRefs?: IAircraftConf[];
}

export class Cdm implements ICdm {
  constructor(
    public id?: number,
    public index?: string,
    public nameFR?: string,
    public nameGB?: string,
    public commentFR?: string,
    public commentGB?: string,
    public docNameFR?: string,
    public docNameGB?: string,
    public busMessage?: string,
    public busWord?: string,
    public mnemonicFR?: string,
    public offset?: number,
    public coding?: string,
    public unitMsg?: string,
    public minMsg?: string,
    public maxMsg?: string,
    public nature?: CdmNature,
    public sign?: boolean,
    public cadrageVTL?: string,
    public minValueVTL?: string,
    public maxValueVTL?: string,
    public minByteVTL?: number,
    public maxByteVTL?: number,
    public unitVTL?: string,
    public kind?: EqptKind,
    public linear?: boolean,
    public func?: number,
    public eqpt?: IEqpt,
    public maintenanceConfiguration?: IMaintenanceConf,
    public libCdms?: ILibCdm[],
    public aircraftConfRefs?: IAircraftConf[]
  ) {
    this.sign = this.sign || false;
    this.linear = this.linear || false;
  }
}
