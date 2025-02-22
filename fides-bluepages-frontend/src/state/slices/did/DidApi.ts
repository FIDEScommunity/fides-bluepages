import { createAsyncThunk } from '@reduxjs/toolkit';
import { setLoading } from '../global';

import { bearerAuth } from '../auth';
import axios from 'axios';
import { addQueryParam } from '../slice';


export const getDids = createAsyncThunk(
    'did/getDids', ({jwtToken, page, pageSize, locale, q}: {
        jwtToken: string | undefined,
        page: number,
        pageSize: number,
        locale: string | undefined,
        q?: string
    }, thunkAPI) => {
        thunkAPI.dispatch(setLoading(true));
        const config = (jwtToken === undefined) ? {} : {
            headers: {'Authorization': bearerAuth(jwtToken)}
        };

        var url = '/webpublic/did';
        url = addQueryParam(url, 'page', page);
        url = addQueryParam(url, 'size', pageSize);
        url = addQueryParam(url, 'locale', locale);
        url = addQueryParam(url, 'q', q);

        return axios.get(url, config)
            .then(response => {
                return response.data
            })
            .finally(() => {
                thunkAPI.dispatch(setLoading(false));
            });
    },
);

export const getDid = createAsyncThunk(
    'did/getDid', ({jwtToken, externalKey, locale}: { jwtToken: string | undefined, externalKey: string | undefined, locale: string | undefined, }, thunkAPI) => {
        thunkAPI.dispatch(setLoading(true));
        const config = (jwtToken === undefined) ? {} : {
            headers: {'Authorization': bearerAuth(jwtToken)}
        };
        var url = '/webpublic/did/' + externalKey;
        url = addQueryParam(url, 'locale', locale);

        return axios.get(url, config)
            .then(response => {
                return response.data
            })
            .finally(() => {
                thunkAPI.dispatch(setLoading(false));
            });
    },
);

export const getDidValidations = createAsyncThunk(
    'did/getDidValidations', ({jwtToken, externalKey, locale}: { jwtToken: string | undefined, externalKey: string | undefined, locale: string | undefined, }, thunkAPI) => {
        thunkAPI.dispatch(setLoading(true));
        const config = (jwtToken === undefined) ? {} : {
            headers: {'Authorization': bearerAuth(jwtToken)}
        };
        var url = '/webpublic/did/' + externalKey + "/validations";
        url = addQueryParam(url, 'locale', locale);
        return axios.get(url, config)
            .then(response => {
                return response.data
            })
            .finally(() => {
                thunkAPI.dispatch(setLoading(false));
            });
    },
);

export const getDidPreview = createAsyncThunk(
    'did/getDidPreview', ({did, locale, performValidations}: { did: string | undefined, locale: string | undefined, performValidations: boolean }, thunkAPI) => {
        thunkAPI.dispatch(setLoading(true));
        var url = '/webpublic/did/preview';
        url = addQueryParam(url, 'locale', locale);

        const body = {
            did: did,
            performValidations: performValidations
        }

        return axios.post(url, body)
            .then(response => {
                return response.data
            })
            .finally(() => {
                thunkAPI.dispatch(setLoading(false));
            });
    },
);

export const addDid = createAsyncThunk(
    'did/addDid', ({did, locale}: { did: string | undefined, locale: string | undefined, }, thunkAPI) => {
        thunkAPI.dispatch(setLoading(true));
        var url = '/webpublic/did';
        url = addQueryParam(url, 'locale', locale);

        const body = {
            did: did
        }
        return axios.post(url, body)
            .then(response => {
                return response.data
            })
            .finally(() => {
                thunkAPI.dispatch(setLoading(false));
            });
    },
);

