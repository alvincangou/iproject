import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Freelancer from './freelancer';
import FreelancerDetail from './freelancer-detail';
import FreelancerUpdate from './freelancer-update';
import FreelancerDeleteDialog from './freelancer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FreelancerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FreelancerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FreelancerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Freelancer} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FreelancerDeleteDialog} />
  </>
);

export default Routes;
