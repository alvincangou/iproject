import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './freelancer.reducer';
import { IFreelancer } from 'app/shared/model/freelancer.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFreelancerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FreelancerUpdate = (props: IFreelancerUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { freelancerEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/freelancer');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...freelancerEntity,
        ...values,
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
          <h2 id="iprojectApp.freelancer.home.createOrEditLabel">
            <Translate contentKey="iprojectApp.freelancer.home.createOrEditLabel">Create or edit a Freelancer</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : freelancerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="freelancer-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="freelancer-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="postalCodeLabel" for="freelancer-postalCode">
                  <Translate contentKey="iprojectApp.freelancer.postalCode">Postal Code</Translate>
                </Label>
                <AvField id="freelancer-postalCode" type="text" name="postalCode" />
              </AvGroup>
              <AvGroup>
                <Label id="creationDateLabel" for="freelancer-creationDate">
                  <Translate contentKey="iprojectApp.freelancer.creationDate">Creation Date</Translate>
                </Label>
                <AvField id="freelancer-creationDate" type="date" className="form-control" name="creationDate" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/freelancer" replace color="info">
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
  freelancerEntity: storeState.freelancer.entity,
  loading: storeState.freelancer.loading,
  updating: storeState.freelancer.updating,
  updateSuccess: storeState.freelancer.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FreelancerUpdate);
