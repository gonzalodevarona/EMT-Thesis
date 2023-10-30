import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { ReducersMapObject, combineReducers } from '@reduxjs/toolkit';

import getStore from 'app/config/store';

import entitiesReducers from './reducers';

import WeightUnit from './EMTMED/weight-unit';
import Supply from './EMTMED/supply';
import Field from './EMTMED/field';
import Location from './EMTMED/location';
import CountingUnit from './EMTMED/counting-unit';
import Batch from './EMTMED/batch';
import Order from './EMTMED/order';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  const store = getStore();
  store.injectReducer('emtmed', combineReducers(entitiesReducers as ReducersMapObject));
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="/weight-unit/*" element={<WeightUnit />} />
        <Route path="/supply/*" element={<Supply />} />
        <Route path="/field/*" element={<Field />} />
        <Route path="/location/*" element={<Location />} />
        <Route path="/counting-unit/*" element={<CountingUnit />} />
        <Route path="/batch/*" element={<Batch />} />
        <Route path="/order/*" element={<Order />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
