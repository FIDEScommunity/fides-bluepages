import React, { FC, PropsWithChildren } from "react";
import { AuthProvider } from 'react-oidc-context';


interface AuthenticationProviderProps {
}

export const AuthenticationProvider: FC<AuthenticationProviderProps & PropsWithChildren> = (props) => {
    const authority = (window.location.hostname === 'wallet.credenco.com')
        ? 'https://iam.credenco.com/realms/fides'
        : 'https://iam.acc.credenco.com/realms/fides';
    const oidcConfig = {
        authority: authority,
        client_id: 'fides-bluepages-frontend',
        redirect_uri: window.location.origin + '/'
    };

    return (
        <AuthProvider {...oidcConfig}>
            {props.children}
        </AuthProvider>
    )
};
