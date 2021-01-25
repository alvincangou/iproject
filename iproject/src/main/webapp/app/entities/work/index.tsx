import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Work from './work';
import WorkDetail from './work-detail';
import WorkUpdate from './work-update';
import WorkDeleteDialog from './work-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WorkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WorkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WorkDetail} />
      <ErrorBoundaryRoute path={match.url} component={Work} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WorkDeleteDialog} />
  </>
);

export default Routes;
