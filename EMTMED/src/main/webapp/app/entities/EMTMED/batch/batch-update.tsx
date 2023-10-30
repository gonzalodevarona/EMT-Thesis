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
import { IBatch } from 'app/shared/model/EMTMED/batch.model';
import { BatchStatus } from 'app/shared/model/enumerations/batch-status.model';
import { getEntity, updateEntity, createEntity, reset } from './batch.reducer';

export const BatchUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const supplies = useAppSelector(state => state.emtmed.supply.entities);
  const batchEntity = useAppSelector(state => state.emtmed.batch.entity);
  const loading = useAppSelector(state => state.emtmed.batch.loading);
  const updating = useAppSelector(state => state.emtmed.batch.updating);
  const updateSuccess = useAppSelector(state => state.emtmed.batch.updateSuccess);
  const batchStatusValues = Object.keys(BatchStatus);

  const handleClose = () => {
    navigate('/emtmed/batch');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSupplies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...batchEntity,
      ...values,
      supply: supplies.find(it => it.id.toString() === values.supply.toString()),
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
          status: 'RED',
          ...batchEntity,
          supply: batchEntity?.supply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emtmedApp.emtmedBatch.home.createOrEditLabel" data-cy="BatchCreateUpdateHeading">
            <Translate contentKey="emtmedApp.emtmedBatch.home.createOrEditLabel">Create or edit a Batch</Translate>
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
                  id="batch-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('emtmedApp.emtmedBatch.manufacturer')}
                id="batch-manufacturer"
                name="manufacturer"
                data-cy="manufacturer"
                type="text"
              />
              <ValidatedField
                label={translate('emtmedApp.emtmedBatch.administrationRoute')}
                id="batch-administrationRoute"
                name="administrationRoute"
                data-cy="administrationRoute"
                type="text"
              />
              <ValidatedField
                label={translate('emtmedApp.emtmedBatch.expirationDate')}
                id="batch-expirationDate"
                name="expirationDate"
                data-cy="expirationDate"
                type="date"
              />
              <ValidatedField
                label={translate('emtmedApp.emtmedBatch.status')}
                id="batch-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {batchStatusValues.map(batchStatus => (
                  <option value={batchStatus} key={batchStatus}>
                    {translate('emtmedApp.BatchStatus.' + batchStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="batch-supply"
                name="supply"
                data-cy="supply"
                label={translate('emtmedApp.emtmedBatch.supply')}
                type="select"
              >
                <option value="" key="0" />
                {supplies
                  ? supplies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/emtmed/batch" replace color="info">
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

export default BatchUpdate;
