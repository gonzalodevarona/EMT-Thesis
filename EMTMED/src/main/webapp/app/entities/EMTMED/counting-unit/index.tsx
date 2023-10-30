import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CountingUnit from './counting-unit';
import CountingUnitDetail from './counting-unit-detail';
import CountingUnitUpdate from './counting-unit-update';
import CountingUnitDeleteDialog from './counting-unit-delete-dialog';

const CountingUnitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CountingUnit />} />
    <Route path="new" element={<CountingUnitUpdate />} />
    <Route path=":id">
      <Route index element={<CountingUnitDetail />} />
      <Route path="edit" element={<CountingUnitUpdate />} />
      <Route path="delete" element={<CountingUnitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CountingUnitRoutes;
