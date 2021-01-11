import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './freelancer.reducer';
import { IFreelancer } from 'app/shared/model/freelancer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFreelancerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FreelancerDetail = (props: IFreelancerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { freelancerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="iprojectApp.freelancer.detail.title">Freelancer</Translate> [<b>{freelancerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="postalCode">
              <Translate contentKey="iprojectApp.freelancer.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{freelancerEntity.postalCode}</dd>
          <dt>
            <span id="creationDate">
              <Translate contentKey="iprojectApp.freelancer.creationDate">Creation Date</Translate>
            </span>
          </dt>
          <dd>
            {freelancerEntity.creationDate ? (
              <TextFormat value={freelancerEntity.creationDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/freelancer" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/freelancer/${freelancerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ freelancer }: IRootState) => ({
  freelancerEntity: freelancer.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FreelancerDetail);
