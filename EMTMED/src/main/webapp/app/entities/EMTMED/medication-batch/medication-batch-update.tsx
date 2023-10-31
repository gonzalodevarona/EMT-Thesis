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
import { IMedicationBatch } from 'app/shared/model/EMTMED/medication-batch.model';
import { BatchStatus } from 'app/shared/model/enumerations/batch-status.model';
import { getEntity, updateEntity, createEntity, reset } from './medication-batch.reducer';

export const MedicationBatchUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const supplies = useAppSelector(state => state.emtmed.supply.entities);
  const medicationBatchEntity = useAppSelector(state => state.emtmed.medicationBatch.entity);
  const loading = useAppSelector(state => state.emtmed.medicationBatch.loading);
  const updating = useAppSelector(state => state.emtmed.medicationBatch.updating);
  const updateSuccess = useAppSelector(state => state.emtmed.medicationBatch.updateSuccess);
  const medicationBatchStatusValues = Object.keys(BatchStatus);

  const handleClose = () => {
    navigate('/emtmed/medication-batch');
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
      ...medicationBatchEntity,
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
          ...medicationBatchEntity,
          supply: medicationBatchEntity?.supply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emtmedApp.emtmedMedicationBatch.home.createOrEditLabel" data-cy="BatchCreateUpdateHeading">
            <Translate contentKey="emtmedApp.emtmedMedicationBatch.home.createOrEditLabel">Create or edit a MedicationBatch</Translate>
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
                  id="medication-batch-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('emtmedApp.emtmedMedicationBatch.manufacturer')}
                id="medication-batch-manufacturer"
                name="manufacturer"
                data-cy="manufacturer"
                type="text"
              />
              <ValidatedField
                label={translate('emtmedApp.emtmedMedicationBatch.administrationRoute')}
                id="medication-batch-administrationRoute"
                name="administrationRoute"
                data-cy="administrationRoute"
                type="text"
              />
              <ValidatedField
                label={translate('emtmedApp.emtmedMedicationBatch.expirationDate')}
                id="medication-batch-expirationDate"
                name="expirationDate"
                data-cy="expirationDate"
                type="date"
              />
              <ValidatedField
                label={translate('emtmedApp.emtmedMedicationBatch.status')}
                id="medication-batch-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {medicationBatchStatusValues.map(medicationBatchStatus => (
                  <option value={medicationBatchStatus} key={medicationBatchStatus}>
                    {translate('emtmedApp.BatchStatus.' + medicationBatchStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="medication-batch-supply"
                name="supply"
                data-cy="supply"
                label={translate('emtmedApp.emtmedMedicationBatch.supply')}
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
              <ValidatedField
                label={translate('emtmedApp.emtmedMedicationBatch.quantity')}
                id="medication-batch-quantity"
                name="quantity"
                data-cy="quantity"
                type="number"
              />
              <ValidatedField
                label={translate('emtmedApp.emtmedMedicationBatch.cum')}
                id="medication-batch-cum"
                name="cum"
                data-cy="cum"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/emtmed/medicationBatch" replace color="info">
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

export default MedicationBatchUpdate;
