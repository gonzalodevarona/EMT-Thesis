import weightUnit from 'app/entities/EMTMED/weight-unit/weight-unit.reducer';
import medicationBatch from 'app/entities/EMTMED/medication-batch/medication-batch.reducer';
import supply from 'app/entities/EMTMED/supply/supply.reducer';
import field from 'app/entities/EMTMED/field/field.reducer';
import location from 'app/entities/EMTMED/location/location.reducer';
import countingUnit from 'app/entities/EMTMED/counting-unit/counting-unit.reducer';
import batch from 'app/entities/EMTMED/batch/batch.reducer';
import order from 'app/entities/EMTMED/order/order.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  weightUnit,
  supply,
  field,
  location,
  countingUnit,
  batch,
  order,
  medicationBatch
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
