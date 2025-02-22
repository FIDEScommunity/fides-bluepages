import React, { FC, ReactNode } from 'react';
import { useAuth } from 'react-oidc-context';

export interface AuthenticationStateHandlerProps {
    authenticated?: ReactNode;
    unAuthenticated?: ReactNode;
    authenticationExpired?: ReactNode;
    loading?: ReactNode;
}

export const AuthenticationStateHandler: FC<AuthenticationStateHandlerProps> = (props) => {

    let auth = useAuth();
    switch (auth.activeNavigator) {
        case "signinSilent":
            return <div>Signing you in...</div>;
        case "signoutRedirect":
            return <div>{props.authenticationExpired!}</div>
        default:
            if (auth.activeNavigator !== undefined) {
                console.log('QQQ auth.activeNavigator: ', auth.activeNavigator);
            }
    }

    if (auth.isLoading) {
        return <div>{props.loading}</div>
    }

    if (auth.error) {
        return <div>Oops... {auth.error?.message}</div>;
    }

    return (<div>{auth.isAuthenticated ? props.authenticated : props.unAuthenticated}</div>)

};
export default AuthenticationStateHandler;
