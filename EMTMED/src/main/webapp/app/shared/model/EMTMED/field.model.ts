import { ISupply } from 'app/shared/model/EMTMED/supply.model';
import { IOrder } from 'app/shared/model/EMTMED/order.model';

export interface IField {
  id?: number;
  value?: string | null;
  name?: string | null;
  supplies?: ISupply[] | null;
  orders?: IOrder[] | null;
}

export const defaultValue: Readonly<IField> = {};
