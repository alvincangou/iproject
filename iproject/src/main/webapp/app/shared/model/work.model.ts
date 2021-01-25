import { IFreelancer } from 'app/shared/model/freelancer.model';
import { IFreelance } from 'app/shared/model/freelance.model';

export interface IWork {
  id?: number;
  name?: string;
  necessaryTime?: number;
  price?: number;
  doneBy?: IFreelancer;
  executedOns?: IFreelance[];
}

export const defaultValue: Readonly<IWork> = {};
