import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICountingUnit } from 'app/shared/model/EMTMED/counting-unit.model';
import { getEntities } from './counting-unit.reducer';

export const CountingUnit = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const countingUnitList = useAppSelector(state => state.emtmed.countingUnit.entities);
  const loading = useAppSelector(state => state.emtmed.countingUnit.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="counting-unit-heading" data-cy="CountingUnitHeading">
        <Translate contentKey="emtmedApp.emtmedCountingUnit.home.title">Counting Units</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="emtmedApp.emtmedCountingUnit.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/emtmed/counting-unit/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="emtmedApp.emtmedCountingUnit.home.createLabel">Create new Counting Unit</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {countingUnitList && countingUnitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="emtmedApp.emtmedCountingUnit.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedCountingUnit.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {countingUnitList.map((countingUnit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/emtmed/counting-unit/${countingUnit.id}`} color="link" size="sm">
                      {countingUnit.id}
                    </Button>
                  </td>
                  <td>{countingUnit.name}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/emtmed/counting-unit/${countingUnit.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/emtmed/counting-unit/${countingUnit.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/emtmed/counting-unit/${countingUnit.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="emtmedApp.emtmedCountingUnit.home.notFound">No Counting Units found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CountingUnit;
