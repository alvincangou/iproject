import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWork, defaultValue } from 'app/shared/model/work.model';

export const ACTION_TYPES = {
  FETCH_WORK_LIST: 'work/FETCH_WORK_LIST',
  FETCH_WORK: 'work/FETCH_WORK',
  CREATE_WORK: 'work/CREATE_WORK',
  UPDATE_WORK: 'work/UPDATE_WORK',
  DELETE_WORK: 'work/DELETE_WORK',
  RESET: 'work/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWork>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type WorkState = Readonly<typeof initialState>;

// Reducer

export default (state: WorkState = initialState, action): WorkState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WORK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WORK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_WORK):
    case REQUEST(ACTION_TYPES.UPDATE_WORK):
    case REQUEST(ACTION_TYPES.DELETE_WORK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_WORK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WORK):
    case FAILURE(ACTION_TYPES.CREATE_WORK):
    case FAILURE(ACTION_TYPES.UPDATE_WORK):
    case FAILURE(ACTION_TYPES.DELETE_WORK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_WORK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_WORK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_WORK):
    case SUCCESS(ACTION_TYPES.UPDATE_WORK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_WORK):
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

const apiUrl = 'api/works';

// Actions

export const getEntities: ICrudGetAllAction<IWork> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WORK_LIST,
  payload: axios.get<IWork>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IWork> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WORK,
    payload: axios.get<IWork>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IWork> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WORK,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWork> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WORK,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWork> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WORK,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
