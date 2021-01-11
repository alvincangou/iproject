import { IFreelance } from 'app/shared/model/freelance.model';

export interface IService {
  id?: number;
  name?: string;
  necessaryTime?: number;
  price?: string;
  ability?: IFreelance;
}

export const defaultValue: Readonly<IService> = {};
