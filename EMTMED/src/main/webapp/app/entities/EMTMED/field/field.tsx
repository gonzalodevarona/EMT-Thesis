import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IField } from 'app/shared/model/EMTMED/field.model';
import { getEntities } from './field.reducer';

export const Field = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const fieldList = useAppSelector(state => state.emtmed.field.entities);
  const loading = useAppSelector(state => state.emtmed.field.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="field-heading" data-cy="FieldHeading">
        <Translate contentKey="emtmedApp.emtmedField.home.title">Fields</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="emtmedApp.emtmedField.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/emtmed/field/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="emtmedApp.emtmedField.home.createLabel">Create new Field</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fieldList && fieldList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="emtmedApp.emtmedField.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedField.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="emtmedApp.emtmedField.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fieldList.map((field, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/emtmed/field/${field.id}`} color="link" size="sm">
                      {field.id}
                    </Button>
                  </td>
                  <td>{field.value}</td>
                  <td>{field.name}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/emtmed/field/${field.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/emtmed/field/${field.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/emtmed/field/${field.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="emtmedApp.emtmedField.home.notFound">No Fields found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Field;
