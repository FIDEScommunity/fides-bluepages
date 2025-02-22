import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface GlobalState {
    isLoading: boolean;
    menuOpen: boolean;
}

const INITIAL_STATE: GlobalState = {
    isLoading: false,
    menuOpen: false
};

export const globalSlice = createSlice({
    name: 'global',
    initialState: INITIAL_STATE,
    reducers: {
        setLoading: (state: GlobalState, action: PayloadAction<boolean>) => {
            return {
                ...state,
                isLoading: action.payload
            };
        },
        showMenu: (state: GlobalState, action: PayloadAction<boolean>) => {
            return {
                ...state,
                menuOpen: action.payload
            };
        },
    }
});

export const {setLoading, showMenu} = globalSlice.actions;
