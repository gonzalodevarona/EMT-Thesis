import { ISupply } from 'app/shared/model/EMTMED/supply.model';

export interface IWeightUnit {
  id?: number;
  name?: string | null;
  supplies?: ISupply[] | null;
}

export const defaultValue: Readonly<IWeightUnit> = {};
