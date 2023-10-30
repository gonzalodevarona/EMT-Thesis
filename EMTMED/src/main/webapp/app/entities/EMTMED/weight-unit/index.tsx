import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WeightUnit from './weight-unit';
import WeightUnitDetail from './weight-unit-detail';
import WeightUnitUpdate from './weight-unit-update';
import WeightUnitDeleteDialog from './weight-unit-delete-dialog';

const WeightUnitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WeightUnit />} />
    <Route path="new" element={<WeightUnitUpdate />} />
    <Route path=":id">
      <Route index element={<WeightUnitDetail />} />
      <Route path="edit" element={<WeightUnitUpdate />} />
      <Route path="delete" element={<WeightUnitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WeightUnitRoutes;
