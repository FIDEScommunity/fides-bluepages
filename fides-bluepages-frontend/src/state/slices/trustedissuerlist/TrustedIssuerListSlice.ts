import { createSlice } from '@reduxjs/toolkit';
import { defaultGenericState, GenericState, TrustedIssuerList } from '../model';
import { addGenericStateListBuilders } from '../slice';
import { getTrustedIssuerList } from './TrustedIssuerListApi';


export interface TrustedIssuerListState extends GenericState<TrustedIssuerList> {
}

export const trustedIssuerListSlice = createSlice({
    name: 'trustedIssuerList',
    initialState: defaultGenericState,
    reducers: {},
    extraReducers: builder => {
        addGenericStateListBuilders(builder, getTrustedIssuerList);
    },
});
