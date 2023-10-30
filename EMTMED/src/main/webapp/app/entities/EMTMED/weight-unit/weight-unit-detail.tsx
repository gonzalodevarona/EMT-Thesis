import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './weight-unit.reducer';

export const WeightUnitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const weightUnitEntity = useAppSelector(state => state.emtmed.weightUnit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="weightUnitDetailsHeading">
          <Translate contentKey="emtmedApp.emtmedWeightUnit.detail.title">WeightUnit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{weightUnitEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="emtmedApp.emtmedWeightUnit.name">Name</Translate>
            </span>
          </dt>
          <dd>{weightUnitEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/emtmed/weight-unit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emtmed/weight-unit/${weightUnitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WeightUnitDetail;
