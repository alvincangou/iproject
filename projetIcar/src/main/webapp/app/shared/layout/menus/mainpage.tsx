import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavDropdown } from './menu-components';

export const MainPage = props => (
  <NavItem>
    <NavLink tag={Link} to="mainpage" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>
        <Translate contentKey="global.menu.mainpage">main</Translate>
      </span>
    </NavLink>
  </NavItem>
);
