import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './freelance.reducer';
import { IFreelance } from 'app/shared/model/freelance.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFreelanceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FreelanceDetail = (props: IFreelanceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { freelanceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="iprojectApp.freelance.detail.title">Freelance</Translate> [<b>{freelanceEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <Translate contentKey="iprojectApp.freelance.belong">Belong</Translate>
          </dt>
          <dd>{freelanceEntity.belong ? freelanceEntity.belong.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/freelance" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/freelance/${freelanceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ freelance }: IRootState) => ({
  freelanceEntity: freelance.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FreelanceDetail);
