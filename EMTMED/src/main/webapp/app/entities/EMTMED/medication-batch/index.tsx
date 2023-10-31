import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MedicationBatch from './medication-batch';
import MedicationBatchDetail from './medication-batch-detail';
import MedicationBatchUpdate from './medication-batch-update';
import MedicationBatchDeleteDialog from './medication-batch-delete-dialog';

const MedicationBatchRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MedicationBatch />} />
    <Route path="new" element={<MedicationBatchUpdate />} />
    <Route path=":id">
      <Route index element={<MedicationBatchDetail />} />
      <Route path="edit" element={<MedicationBatchUpdate />} />
      <Route path="delete" element={<MedicationBatchDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MedicationBatchRoutes;
