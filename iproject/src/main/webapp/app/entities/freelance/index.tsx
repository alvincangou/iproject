import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Freelance from './freelance';
import FreelanceDetail from './freelance-detail';
import FreelanceUpdate from './freelance-update';
import FreelanceDeleteDialog from './freelance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FreelanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FreelanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FreelanceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Freelance} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FreelanceDeleteDialog} />
  </>
);

export default Routes;
