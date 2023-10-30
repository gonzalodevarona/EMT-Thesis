import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISupply } from 'app/shared/model/EMTMED/supply.model';
import { getEntities } from './supply.reducer';

export const Supply = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const supplyList = useAppSelector(state => state.emtmed.supply.entities);
  const loading = useAppSelector(state => state.emtmed.supply.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="supply-heading" data-cy="SupplyHeading">
        <Translate contentKey="emtmedApp.emtmedSupply.home.title">Supplies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="emtmedApp.emtmedSupply.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/emtmed/supply/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="emtmedApp.emtmedSupply.home.createLabel">Create new Supply</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {supplyList && supplyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="emtmedApp.emtmedSupply.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedSupply.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedSupply.weight">Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedSupply.quantity">Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedSupply.field">Field</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedSupply.location">Location</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedSupply.order">Order</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedSupply.weightUnit">Weight Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedSupply.countingUnit">Counting Unit</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {supplyList.map((supply, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/emtmed/supply/${supply.id}`} color="link" size="sm">
                      {supply.id}
                    </Button>
                  </td>
                  <td>{supply.name}</td>
                  <td>{supply.weight}</td>
                  <td>{supply.quantity}</td>
                  <td>
                    {supply.fields
                      ? supply.fields.map((val, j) => (
                          <span key={j}>
                            <Link to={`/emtmed/field/${val.id}`}>{val.name}</Link>
                            {j === supply.fields.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{supply.location ? <Link to={`/emtmed/location/${supply.location.id}`}>{supply.location.name}</Link> : ''}</td>
                  <td>{supply.order ? <Link to={`/emtmed/order/${supply.order.id}`}>{supply.order.id}</Link> : ''}</td>
                  <td>
                    {supply.weightUnit ? <Link to={`/emtmed/weight-unit/${supply.weightUnit.id}`}>{supply.weightUnit.name}</Link> : ''}
                  </td>
                  <td>
                    {supply.countingUnit ? (
                      <Link to={`/emtmed/counting-unit/${supply.countingUnit.id}`}>{supply.countingUnit.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/emtmed/supply/${supply.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/emtmed/supply/${supply.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/emtmed/supply/${supply.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="emtmedApp.emtmedSupply.home.notFound">No Supplies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Supply;
