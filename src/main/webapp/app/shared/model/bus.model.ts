export interface IBus {
  id?: number;
  index?: number;
  name?: string;
}

export class Bus implements IBus {
  constructor(public id?: number, public index?: number, public name?: string) {}
}
