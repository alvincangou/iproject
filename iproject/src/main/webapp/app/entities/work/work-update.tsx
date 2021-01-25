import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFreelancer } from 'app/shared/model/freelancer.model';
import { getEntities as getFreelancers } from 'app/entities/freelancer/freelancer.reducer';
import { IFreelance } from 'app/shared/model/freelance.model';
import { getEntities as getFreelances } from 'app/entities/freelance/freelance.reducer';
import { getEntity, updateEntity, createEntity, reset } from './work.reducer';
import { IWork } from 'app/shared/model/work.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWorkUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WorkUpdate = (props: IWorkUpdateProps) => {
  const [idsexecutedOn, setIdsexecutedOn] = useState([]);
  const [doneById, setDoneById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { workEntity, freelancers, freelances, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/work');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFreelancers();
    props.getFreelances();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...workEntity,
        ...values,
        executedOns: mapIdList(values.executedOns),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="iprojectApp.work.home.createOrEditLabel">
            <Translate contentKey="iprojectApp.work.home.createOrEditLabel">Create or edit a Work</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : workEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="work-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="work-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="work-name">
                  <Translate contentKey="iprojectApp.work.name">Name</Translate>
                </Label>
                <AvField id="work-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="necessaryTimeLabel" for="work-necessaryTime">
                  <Translate contentKey="iprojectApp.work.necessaryTime">Necessary Time</Translate>
                </Label>
                <AvField id="work-necessaryTime" type="text" name="necessaryTime" />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="work-price">
                  <Translate contentKey="iprojectApp.work.price">Price</Translate>
                </Label>
                <AvField id="work-price" type="string" className="form-control" name="price" />
              </AvGroup>
              <AvGroup>
                <Label for="work-doneBy">
                  <Translate contentKey="iprojectApp.work.doneBy">Done By</Translate>
                </Label>
                <AvInput id="work-doneBy" type="select" className="form-control" name="doneBy.id">
                  <option value="" key="0" />
                  {freelancers
                    ? freelancers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="work-executedOn">
                  <Translate contentKey="iprojectApp.work.executedOn">Executed On</Translate>
                </Label>
                <AvInput
                  id="work-executedOn"
                  type="select"
                  multiple
                  className="form-control"
                  name="executedOns"
                  value={workEntity.executedOns && workEntity.executedOns.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {freelances
                    ? freelances.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/work" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  freelancers: storeState.freelancer.entities,
  freelances: storeState.freelance.entities,
  workEntity: storeState.work.entity,
  loading: storeState.work.loading,
  updating: storeState.work.updating,
  updateSuccess: storeState.work.updateSuccess,
});

const mapDispatchToProps = {
  getFreelancers,
  getFreelances,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WorkUpdate);
