import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './work.reducer';
import { IWork } from 'app/shared/model/work.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWorkProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Work = (props: IWorkProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { workList, match, loading } = props;
  return (
    <div>
      <h2 id="work-heading">
        <Translate contentKey="iprojectApp.work.home.title">Works</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="iprojectApp.work.home.createLabel">Create new Work</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {workList && workList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="iprojectApp.work.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="iprojectApp.work.necessaryTime">Necessary Time</Translate>
                </th>
                <th>
                  <Translate contentKey="iprojectApp.work.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="iprojectApp.work.doneBy">Done By</Translate>
                </th>
                <th>
                  <Translate contentKey="iprojectApp.work.executedOn">Executed On</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {workList.map((work, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${work.id}`} color="link" size="sm">
                      {work.id}
                    </Button>
                  </td>
                  <td>{work.name}</td>
                  <td>{work.necessaryTime}</td>
                  <td>{work.price}</td>
                  <td>{work.doneBy ? <Link to={`freelancer/${work.doneBy.id}`}>{work.doneBy.id}</Link> : ''}</td>
                  <td>
                    {work.executedOns
                      ? work.executedOns.map((val, j) => (
                          <span key={j}>
                            <Link to={`freelance/${val.id}`}>{val.id}</Link>
                            {j === work.executedOns.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${work.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${work.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${work.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="iprojectApp.work.home.notFound">No Works found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ work }: IRootState) => ({
  workList: work.entities,
  loading: work.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Work);
