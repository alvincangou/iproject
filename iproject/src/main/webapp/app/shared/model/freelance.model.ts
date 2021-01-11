import { IFreelancer } from 'app/shared/model/freelancer.model';

export interface IFreelance {
  id?: number;
  belong?: IFreelancer;
}

export const defaultValue: Readonly<IFreelance> = {};
