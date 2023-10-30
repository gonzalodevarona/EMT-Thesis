import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IField } from 'app/shared/model/EMTMED/field.model';
import { getEntities as getFields } from 'app/entities/EMTMED/field/field.reducer';
import { ILocation } from 'app/shared/model/EMTMED/location.model';
import { getEntities as getLocations } from 'app/entities/EMTMED/location/location.reducer';
import { IOrder } from 'app/shared/model/EMTMED/order.model';
import { getEntities as getOrders } from 'app/entities/EMTMED/order/order.reducer';
import { IWeightUnit } from 'app/shared/model/EMTMED/weight-unit.model';
import { getEntities as getWeightUnits } from 'app/entities/EMTMED/weight-unit/weight-unit.reducer';
import { ICountingUnit } from 'app/shared/model/EMTMED/counting-unit.model';
import { getEntities as getCountingUnits } from 'app/entities/EMTMED/counting-unit/counting-unit.reducer';
import { ISupply } from 'app/shared/model/EMTMED/supply.model';
import { getEntity, updateEntity, createEntity, reset } from './supply.reducer';

export const SupplyUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fields = useAppSelector(state => state.emtmed.field.entities);
  const locations = useAppSelector(state => state.emtmed.location.entities);
  const orders = useAppSelector(state => state.emtmed.order.entities);
  const weightUnits = useAppSelector(state => state.emtmed.weightUnit.entities);
  const countingUnits = useAppSelector(state => state.emtmed.countingUnit.entities);
  const supplyEntity = useAppSelector(state => state.emtmed.supply.entity);
  const loading = useAppSelector(state => state.emtmed.supply.loading);
  const updating = useAppSelector(state => state.emtmed.supply.updating);
  const updateSuccess = useAppSelector(state => state.emtmed.supply.updateSuccess);

  const handleClose = () => {
    navigate('/emtmed/supply');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFields({}));
    dispatch(getLocations({}));
    dispatch(getOrders({}));
    dispatch(getWeightUnits({}));
    dispatch(getCountingUnits({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...supplyEntity,
      ...values,
      fields: mapIdList(values.fields),
      location: locations.find(it => it.id.toString() === values.location.toString()),
      order: orders.find(it => it.id.toString() === values.order.toString()),
      weightUnit: weightUnits.find(it => it.id.toString() === values.weightUnit.toString()),
      countingUnit: countingUnits.find(it => it.id.toString() === values.countingUnit.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...supplyEntity,
          fields: supplyEntity?.fields?.map(e => e.id.toString()),
          location: supplyEntity?.location?.id,
          order: supplyEntity?.order?.id,
          weightUnit: supplyEntity?.weightUnit?.id,
          countingUnit: supplyEntity?.countingUnit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emtmedApp.emtmedSupply.home.createOrEditLabel" data-cy="SupplyCreateUpdateHeading">
            <Translate contentKey="emtmedApp.emtmedSupply.home.createOrEditLabel">Create or edit a Supply</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="supply-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('emtmedApp.emtmedSupply.name')} id="supply-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('emtmedApp.emtmedSupply.weight')}
                id="supply-weight"
                name="weight"
                data-cy="weight"
                type="text"
              />
              <ValidatedField
                label={translate('emtmedApp.emtmedSupply.quantity')}
                id="supply-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
              />
              <ValidatedField
                label={translate('emtmedApp.emtmedSupply.field')}
                id="supply-field"
                data-cy="field"
                type="select"
                multiple
                name="fields"
              >
                <option value="" key="0" />
                {fields
                  ? fields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supply-location"
                name="location"
                data-cy="location"
                label={translate('emtmedApp.emtmedSupply.location')}
                type="select"
              >
                <option value="" key="0" />
                {locations
                  ? locations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supply-order"
                name="order"
                data-cy="order"
                label={translate('emtmedApp.emtmedSupply.order')}
                type="select"
              >
                <option value="" key="0" />
                {orders
                  ? orders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supply-weightUnit"
                name="weightUnit"
                data-cy="weightUnit"
                label={translate('emtmedApp.emtmedSupply.weightUnit')}
                type="select"
              >
                <option value="" key="0" />
                {weightUnits
                  ? weightUnits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supply-countingUnit"
                name="countingUnit"
                data-cy="countingUnit"
                label={translate('emtmedApp.emtmedSupply.countingUnit')}
                type="select"
              >
                <option value="" key="0" />
                {countingUnits
                  ? countingUnits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/emtmed/supply" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SupplyUpdate;
