import { Moment } from 'moment';

export interface IFreelancer {
  id?: number;
  postalCode?: string;
  creationDate?: string;
}

export const defaultValue: Readonly<IFreelancer> = {};
