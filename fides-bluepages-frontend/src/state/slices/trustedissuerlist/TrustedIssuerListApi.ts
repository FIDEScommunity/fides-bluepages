import { createAsyncThunk } from '@reduxjs/toolkit';
import { setLoading } from '../global';
import axios from 'axios';


export const getTrustedIssuerList = createAsyncThunk(
    'trustedIssuerList/getTrustedIssuerList', ({}: {}, thunkAPI) => {
        thunkAPI.dispatch(setLoading(true));
        return axios.get('/webpublic/trustedissuerlist')
            .then(response => {
                return response.data
            })
            .finally(() => {
                thunkAPI.dispatch(setLoading(false));
            });
    },
);



