import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './supply.reducer';

export const SupplyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const supplyEntity = useAppSelector(state => state.emtmed.supply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="supplyDetailsHeading">
          <Translate contentKey="emtmedApp.emtmedSupply.detail.title">Supply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{supplyEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="emtmedApp.emtmedSupply.name">Name</Translate>
            </span>
          </dt>
          <dd>{supplyEntity.name}</dd>
          <dt>
            <span id="weight">
              <Translate contentKey="emtmedApp.emtmedSupply.weight">Weight</Translate>
            </span>
          </dt>
          <dd>{supplyEntity.weight}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="emtmedApp.emtmedSupply.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{supplyEntity.quantity}</dd>
          <dt>
            <Translate contentKey="emtmedApp.emtmedSupply.field">Field</Translate>
          </dt>
          <dd>
            {supplyEntity.fields
              ? supplyEntity.fields.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {supplyEntity.fields && i === supplyEntity.fields.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="emtmedApp.emtmedSupply.location">Location</Translate>
          </dt>
          <dd>{supplyEntity.location ? supplyEntity.location.name : ''}</dd>
          <dt>
            <Translate contentKey="emtmedApp.emtmedSupply.order">Order</Translate>
          </dt>
          <dd>{supplyEntity.order ? supplyEntity.order.id : ''}</dd>
          <dt>
            <Translate contentKey="emtmedApp.emtmedSupply.weightUnit">Weight Unit</Translate>
          </dt>
          <dd>{supplyEntity.weightUnit ? supplyEntity.weightUnit.name : ''}</dd>
          <dt>
            <Translate contentKey="emtmedApp.emtmedSupply.countingUnit">Counting Unit</Translate>
          </dt>
          <dd>{supplyEntity.countingUnit ? supplyEntity.countingUnit.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/emtmed/supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emtmed/supply/${supplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SupplyDetail;
