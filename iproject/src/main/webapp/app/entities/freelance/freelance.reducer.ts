import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFreelance, defaultValue } from 'app/shared/model/freelance.model';

export const ACTION_TYPES = {
  FETCH_FREELANCE_LIST: 'freelance/FETCH_FREELANCE_LIST',
  FETCH_FREELANCE: 'freelance/FETCH_FREELANCE',
  CREATE_FREELANCE: 'freelance/CREATE_FREELANCE',
  UPDATE_FREELANCE: 'freelance/UPDATE_FREELANCE',
  DELETE_FREELANCE: 'freelance/DELETE_FREELANCE',
  RESET: 'freelance/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFreelance>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type FreelanceState = Readonly<typeof initialState>;

// Reducer

export default (state: FreelanceState = initialState, action): FreelanceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FREELANCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FREELANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FREELANCE):
    case REQUEST(ACTION_TYPES.UPDATE_FREELANCE):
    case REQUEST(ACTION_TYPES.DELETE_FREELANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FREELANCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FREELANCE):
    case FAILURE(ACTION_TYPES.CREATE_FREELANCE):
    case FAILURE(ACTION_TYPES.UPDATE_FREELANCE):
    case FAILURE(ACTION_TYPES.DELETE_FREELANCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FREELANCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FREELANCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FREELANCE):
    case SUCCESS(ACTION_TYPES.UPDATE_FREELANCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FREELANCE):
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

const apiUrl = 'api/freelances';

// Actions

export const getEntities: ICrudGetAllAction<IFreelance> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FREELANCE_LIST,
  payload: axios.get<IFreelance>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IFreelance> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FREELANCE,
    payload: axios.get<IFreelance>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFreelance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FREELANCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFreelance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FREELANCE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFreelance> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FREELANCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
