import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBatch } from 'app/shared/model/EMTMED/batch.model';
import { getEntities } from './batch.reducer';

export const Batch = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const batchList = useAppSelector(state => state.emtmed.batch.entities);
  const loading = useAppSelector(state => state.emtmed.batch.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="batch-heading" data-cy="BatchHeading">
        <Translate contentKey="emtmedApp.emtmedBatch.home.title">Batches</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="emtmedApp.emtmedBatch.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/emtmed/batch/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="emtmedApp.emtmedBatch.home.createLabel">Create new Batch</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {batchList && batchList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="emtmedApp.emtmedBatch.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedBatch.manufacturer">Manufacturer</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedBatch.administrationRoute">Administration Route</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedBatch.expirationDate">Expiration Date</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedBatch.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedBatch.supply">Supply</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {batchList.map((batch, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/emtmed/batch/${batch.id}`} color="link" size="sm">
                      {batch.id}
                    </Button>
                  </td>
                  <td>{batch.manufacturer}</td>
                  <td>{batch.administrationRoute}</td>
                  <td>
                    {batch.expirationDate ? <TextFormat type="date" value={batch.expirationDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`emtmedApp.BatchStatus.${batch.status}`} />
                  </td>
                  <td>{batch.supply ? <Link to={`/emtmed/supply/${batch.supply.id}`}>{batch.supply.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/emtmed/batch/${batch.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/emtmed/batch/${batch.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/emtmed/batch/${batch.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="emtmedApp.emtmedBatch.home.notFound">No Batches found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Batch;
