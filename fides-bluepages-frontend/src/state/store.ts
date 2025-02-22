import { Action, combineReducers, configureStore, ThunkAction } from '@reduxjs/toolkit';
import { didSlice, DidState, globalSlice, GlobalState, userSlice, UserState } from './slices';
import { userPreferenceSlice, UserPreferenceState } from './slices/userpreference';
import { toastMessageSlice, ToastMessageState } from './slices/toast';
import { trustedIssuerListSlice, TrustedIssuerListState } from './slices/trustedissuerlist';


export interface ApplicationState {
    globalState: GlobalState;
    userState: UserState;
    didState: DidState;
    userPreferenceState: UserPreferenceState;
    toastMessageState: ToastMessageState;
    trustedIssuerListState: TrustedIssuerListState;
}

const rootReducer = combineReducers<ApplicationState>({
    globalState: globalSlice.reducer,
    userState: userSlice.reducer,
    didState: didSlice.reducer,
    userPreferenceState: userPreferenceSlice.reducer,
    toastMessageState: toastMessageSlice.reducer,
    trustedIssuerListState: trustedIssuerListSlice.reducer,
});

export const store = configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            serializableCheck: false,
        }),
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
    ReturnType,
    RootState,
    unknown,
    Action<string>
>;
