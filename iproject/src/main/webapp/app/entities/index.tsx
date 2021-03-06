import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Freelancer from './freelancer';
import Freelance from './freelance';
import Work from './work';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}freelancer`} component={Freelancer} />
      <ErrorBoundaryRoute path={`${match.url}freelance`} component={Freelance} />
      <ErrorBoundaryRoute path={`${match.url}work`} component={Work} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
