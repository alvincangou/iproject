import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFreelance } from 'app/shared/model/freelance.model';
import { getEntities as getFreelances } from 'app/entities/freelance/freelance.reducer';
import { getEntity, updateEntity, createEntity, reset } from './service.reducer';
import { IService } from 'app/shared/model/service.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceUpdate = (props: IServiceUpdateProps) => {
  const [abilityId, setAbilityId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serviceEntity, freelances, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/service');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...serviceEntity,
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
          <h2 id="icarApp.service.home.createOrEditLabel">
            <Translate contentKey="icarApp.service.home.createOrEditLabel">Create or edit a Service</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : serviceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="service-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="service-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="service-name">
                  <Translate contentKey="icarApp.service.name">Name</Translate>
                </Label>
                <AvField id="service-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="necessaryTimeLabel" for="service-necessaryTime">
                  <Translate contentKey="icarApp.service.necessaryTime">Necessary Time</Translate>
                </Label>
                <AvField id="service-necessaryTime" type="string" className="form-control" name="necessaryTime" />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="service-price">
                  <Translate contentKey="icarApp.service.price">Price</Translate>
                </Label>
                <AvField id="service-price" type="text" name="price" />
              </AvGroup>
              <AvGroup>
                <Label for="service-ability">
                  <Translate contentKey="icarApp.service.ability">Ability</Translate>
                </Label>
                <AvInput id="service-ability" type="select" className="form-control" name="ability.id">
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
              <Button tag={Link} id="cancel-save" to="/service" replace color="info">
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
  freelances: storeState.freelance.entities,
  serviceEntity: storeState.service.entity,
  loading: storeState.service.loading,
  updating: storeState.service.updating,
  updateSuccess: storeState.service.updateSuccess,
});

const mapDispatchToProps = {
  getFreelances,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUpdate);
