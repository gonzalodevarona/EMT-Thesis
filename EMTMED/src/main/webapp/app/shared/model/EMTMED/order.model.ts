import dayjs from 'dayjs';
import { ISupply } from 'app/shared/model/EMTMED/supply.model';
import { IField } from 'app/shared/model/EMTMED/field.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

export interface IOrder {
  id?: number;
  practitioner?: number | null;
  authoredOn?: string | null;
  status?: OrderStatus | null;
  supplies?: ISupply[] | null;
  fields?: IField[] | null;
}

export const defaultValue: Readonly<IOrder> = {};
