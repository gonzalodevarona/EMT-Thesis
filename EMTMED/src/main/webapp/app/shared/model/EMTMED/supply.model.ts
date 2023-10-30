import { IBatch } from 'app/shared/model/EMTMED/batch.model';
import { IField } from 'app/shared/model/EMTMED/field.model';
import { ILocation } from 'app/shared/model/EMTMED/location.model';
import { IOrder } from 'app/shared/model/EMTMED/order.model';
import { IWeightUnit } from 'app/shared/model/EMTMED/weight-unit.model';
import { ICountingUnit } from 'app/shared/model/EMTMED/counting-unit.model';

export interface ISupply {
  id?: number;
  name?: string | null;
  weight?: number | null;
  quantity?: number | null;
  batches?: IBatch[] | null;
  fields?: IField[] | null;
  location?: ILocation | null;
  order?: IOrder | null;
  weightUnit?: IWeightUnit | null;
  countingUnit?: ICountingUnit | null;
}

export const defaultValue: Readonly<ISupply> = {};
