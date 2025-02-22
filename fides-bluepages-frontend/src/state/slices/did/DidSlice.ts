import { createSlice } from '@reduxjs/toolkit';
import { defaultGenericPagableState, Did, GenericPageableState } from '../model';
import { addGenericPageableStateListBuilders, addGenericPageableStateSingleBuilders } from '../slice';
import { addDid, getDid, getDidPreview, getDids, getDidValidations } from './DidApi';


export interface DidState extends GenericPageableState<Did> {
}

export const didSlice = createSlice({
    name: 'did',
    initialState: defaultGenericPagableState,
    reducers: {},
    extraReducers: builder => {
        addGenericPageableStateListBuilders(builder, getDids);
        addGenericPageableStateSingleBuilders(builder, getDid);
        addGenericPageableStateSingleBuilders(builder, getDidValidations);
        addGenericPageableStateSingleBuilders(builder, getDidPreview);
        addGenericPageableStateSingleBuilders(builder, addDid);
    },
});

