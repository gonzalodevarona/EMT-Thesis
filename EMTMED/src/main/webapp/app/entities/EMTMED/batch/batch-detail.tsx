import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './batch.reducer';

export const BatchDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const batchEntity = useAppSelector(state => state.emtmed.batch.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="batchDetailsHeading">
          <Translate contentKey="emtmedApp.emtmedBatch.detail.title">Batch</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{batchEntity.id}</dd>
          <dt>
            <span id="manufacturer">
              <Translate contentKey="emtmedApp.emtmedBatch.manufacturer">Manufacturer</Translate>
            </span>
          </dt>
          <dd>{batchEntity.manufacturer}</dd>
          <dt>
            <span id="administrationRoute">
              <Translate contentKey="emtmedApp.emtmedBatch.administrationRoute">Administration Route</Translate>
            </span>
          </dt>
          <dd>{batchEntity.administrationRoute}</dd>
          <dt>
            <span id="expirationDate">
              <Translate contentKey="emtmedApp.emtmedBatch.expirationDate">Expiration Date</Translate>
            </span>
          </dt>
          <dd>
            {batchEntity.expirationDate ? (
              <TextFormat value={batchEntity.expirationDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="emtmedApp.emtmedBatch.status">Status</Translate>
            </span>
          </dt>
          <dd>{batchEntity.status}</dd>
          <dt>
            <Translate contentKey="emtmedApp.emtmedBatch.supply">Supply</Translate>
          </dt>
          <dd>{batchEntity.supply ? batchEntity.supply.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/emtmed/batch" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emtmed/batch/${batchEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BatchDetail;
