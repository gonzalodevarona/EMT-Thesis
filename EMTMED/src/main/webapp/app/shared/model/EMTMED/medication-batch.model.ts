import dayjs from 'dayjs';
import { ISupply } from 'app/shared/model/EMTMED/supply.model';
import { BatchStatus } from 'app/shared/model/enumerations/batch-status.model';

export interface IMedicationBatch {
  id?: number;
  manufacturer?: string | null;
  administrationRoute?: string | null;
  expirationDate?: string | null;
  status?: BatchStatus | null;
  supply?: ISupply | null;
  quantity?: string | null;
  cum?: string | null;
}

export const defaultValue: Readonly<IMedicationBatch> = {};
