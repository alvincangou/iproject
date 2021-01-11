import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFreelancer, defaultValue } from 'app/shared/model/freelancer.model';

export const ACTION_TYPES = {
  FETCH_FREELANCER_LIST: 'freelancer/FETCH_FREELANCER_LIST',
  FETCH_FREELANCER: 'freelancer/FETCH_FREELANCER',
  CREATE_FREELANCER: 'freelancer/CREATE_FREELANCER',
  UPDATE_FREELANCER: 'freelancer/UPDATE_FREELANCER',
  DELETE_FREELANCER: 'freelancer/DELETE_FREELANCER',
  RESET: 'freelancer/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFreelancer>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type FreelancerState = Readonly<typeof initialState>;

// Reducer

export default (state: FreelancerState = initialState, action): FreelancerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FREELANCER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FREELANCER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FREELANCER):
    case REQUEST(ACTION_TYPES.UPDATE_FREELANCER):
    case REQUEST(ACTION_TYPES.DELETE_FREELANCER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FREELANCER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FREELANCER):
    case FAILURE(ACTION_TYPES.CREATE_FREELANCER):
    case FAILURE(ACTION_TYPES.UPDATE_FREELANCER):
    case FAILURE(ACTION_TYPES.DELETE_FREELANCER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FREELANCER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FREELANCER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FREELANCER):
    case SUCCESS(ACTION_TYPES.UPDATE_FREELANCER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FREELANCER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/freelancers';

// Actions

export const getEntities: ICrudGetAllAction<IFreelancer> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FREELANCER_LIST,
  payload: axios.get<IFreelancer>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IFreelancer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FREELANCER,
    payload: axios.get<IFreelancer>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFreelancer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FREELANCER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFreelancer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FREELANCER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFreelancer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FREELANCER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
