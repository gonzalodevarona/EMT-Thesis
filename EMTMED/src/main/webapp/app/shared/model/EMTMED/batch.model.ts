import dayjs from 'dayjs';
import { ISupply } from 'app/shared/model/EMTMED/supply.model';
import { BatchStatus } from 'app/shared/model/enumerations/batch-status.model';

export interface IBatch {
  id?: number;
  manufacturer?: string | null;
  administrationRoute?: string | null;
  expirationDate?: string | null;
  status?: BatchStatus | null;
  supply?: ISupply | null;
}

export const defaultValue: Readonly<IBatch> = {};
