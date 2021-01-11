import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service.reducer';
import { IService } from 'app/shared/model/service.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceDetail = (props: IServiceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="icarApp.service.detail.title">Service</Translate> [<b>{serviceEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="icarApp.service.name">Name</Translate>
            </span>
          </dt>
          <dd>{serviceEntity.name}</dd>
          <dt>
            <span id="necessaryTime">
              <Translate contentKey="icarApp.service.necessaryTime">Necessary Time</Translate>
            </span>
          </dt>
          <dd>{serviceEntity.necessaryTime}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="icarApp.service.price">Price</Translate>
            </span>
          </dt>
          <dd>{serviceEntity.price}</dd>
          <dt>
            <Translate contentKey="icarApp.service.ability">Ability</Translate>
          </dt>
          <dd>{serviceEntity.ability ? serviceEntity.ability.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service/${serviceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ service }: IRootState) => ({
  serviceEntity: service.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceDetail);
