import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISupply } from 'app/shared/model/EMTMED/supply.model';
import { getEntities as getSupplies } from 'app/entities/EMTMED/supply/supply.reducer';
import { IOrder } from 'app/shared/model/EMTMED/order.model';
import { getEntities as getOrders } from 'app/entities/EMTMED/order/order.reducer';
import { IField } from 'app/shared/model/EMTMED/field.model';
import { getEntity, updateEntity, createEntity, reset } from './field.reducer';

export const FieldUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const supplies = useAppSelector(state => state.emtmed.supply.entities);
  const orders = useAppSelector(state => state.emtmed.order.entities);
  const fieldEntity = useAppSelector(state => state.emtmed.field.entity);
  const loading = useAppSelector(state => state.emtmed.field.loading);
  const updating = useAppSelector(state => state.emtmed.field.updating);
  const updateSuccess = useAppSelector(state => state.emtmed.field.updateSuccess);

  const handleClose = () => {
    navigate('/emtmed/field');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSupplies({}));
    dispatch(getOrders({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fieldEntity,
      ...values,
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
          ...fieldEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emtmedApp.emtmedField.home.createOrEditLabel" data-cy="FieldCreateUpdateHeading">
            <Translate contentKey="emtmedApp.emtmedField.home.createOrEditLabel">Create or edit a Field</Translate>
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
                  id="field-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('emtmedApp.emtmedField.value')} id="field-value" name="value" data-cy="value" type="text" />
              <ValidatedField label={translate('emtmedApp.emtmedField.name')} id="field-name" name="name" data-cy="name" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/emtmed/field" replace color="info">
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

export default FieldUpdate;
