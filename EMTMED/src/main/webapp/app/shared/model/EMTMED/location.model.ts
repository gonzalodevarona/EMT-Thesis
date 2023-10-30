import { ISupply } from 'app/shared/model/EMTMED/supply.model';

export interface ILocation {
  id?: number;
  name?: string | null;
  supplies?: ISupply[] | null;
}

export const defaultValue: Readonly<ILocation> = {};
