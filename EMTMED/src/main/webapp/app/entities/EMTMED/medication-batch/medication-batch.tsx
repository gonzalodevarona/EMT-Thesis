import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMedicationBatch } from 'app/shared/model/EMTMED/medication-batch.model';
import { getEntities } from './medication-batch.reducer';

export const MedicationBatch = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const medicationBatchList = useAppSelector(state => state.emtmed.medicationBatch.entities);
  const loading = useAppSelector(state => state.emtmed.medicationBatch.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="medication-batch-heading" data-cy="MedicationBatchHeading">
        <Translate contentKey="emtmedApp.emtmedMedicationBatch.home.title">Medication Batches</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="emtmedApp.emtmedMedicationBatch.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/emtmed/medication-batch/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="emtmedApp.emtmedMedicationBatch.home.createLabel">Create new Medication Batch</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {medicationBatchList && medicationBatchList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="emtmedApp.emtmedMedicationBatch.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedMedicationBatch.manufacturer">Manufacturer</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedMedicationBatch.administrationRoute">Administration Route</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedMedicationBatch.expirationDate">Expiration Date</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedMedicationBatch.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedMedicationBatch.supply">Supply</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedMedicationBatch.quantity">Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedMedicationBatch.cum">Cum</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {console.log(medicationBatchList)}
              {medicationBatchList.map((medicationBatch, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/emtmed/medication-batch/${medicationBatch.id}`} color="link" size="sm">
                      {medicationBatch.id}
                    </Button>
                  </td>
                  <td>{medicationBatch.manufacturer}</td>
                  <td>{medicationBatch.administrationRoute}</td>
                  <td>
                    {medicationBatch.expirationDate ? <TextFormat type="date" value={medicationBatch.expirationDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`emtmedApp.BatchStatus.${medicationBatch.status}`} />
                  </td>
                  <td>{medicationBatch.supply ? <Link to={`/emtmed/supply/${medicationBatch.supply.id}`}>{medicationBatch.supply.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/emtmed/medication-batch/${medicationBatch.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/emtmed/medication-batch/${medicationBatch.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/emtmed/medication-batch/${medicationBatch.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="emtmedApp.emtmedMedicationBatch.home.notFound">No Medication Batches found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MedicationBatch;
