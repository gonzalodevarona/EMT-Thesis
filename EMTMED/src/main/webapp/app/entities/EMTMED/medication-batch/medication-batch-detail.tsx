import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './medication-batch.reducer';

export const MedicationBatchDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const medicationBatchEntity = useAppSelector(state => state.emtmed.medicationBatch.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="medicationBatchDetailsHeading">
          <Translate contentKey="emtmedApp.emtmedMedicationBatch.detail.title">Medication Batch</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{medicationBatchEntity.id}</dd>
          <dt>
            <span id="manufacturer">
              <Translate contentKey="emtmedApp.emtmedMedicationBatch.manufacturer">Manufacturer</Translate>
            </span>
          </dt>
          <dd>{medicationBatchEntity.manufacturer}</dd>
          <dt>
            <span id="administrationRoute">
              <Translate contentKey="emtmedApp.emtmedMedicationBatch.administrationRoute">Administration Route</Translate>
            </span>
          </dt>
          <dd>{medicationBatchEntity.administrationRoute}</dd>
          <dt>
            <span id="expirationDate">
              <Translate contentKey="emtmedApp.emtmedMedicationBatch.expirationDate">Expiration Date</Translate>
            </span>
          </dt>
          <dd>
            {medicationBatchEntity.expirationDate ? (
              <TextFormat value={medicationBatchEntity.expirationDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="emtmedApp.emtmedMedicationBatch.status">Status</Translate>
            </span>
          </dt>
          <dd>{medicationBatchEntity.status}</dd>
          <dt>
            <Translate contentKey="emtmedApp.emtmedMedicationBatch.supply">Supply</Translate>
          </dt>
          <dd>{medicationBatchEntity.supply ? medicationBatchEntity.supply.name : ''}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="emtmedApp.emtmedMedicationBatch.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{medicationBatchEntity.quantity}</dd>
          <dt>
            <span id="cum">
              <Translate contentKey="emtmedApp.emtmedMedicationBatch.cum">Cum</Translate>
            </span>
          </dt>
          <dd>{medicationBatchEntity.cum}</dd>
        </dl>
        <Button tag={Link} to="/emtmed/medication-batch" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emtmed/medication-batch/${medicationBatchEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MedicationBatchDetail;


