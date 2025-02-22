import { FC } from 'react';
import { useAuth } from 'react-oidc-context';

export const Login: FC = () => {
    let auth = useAuth();
    auth.signinRedirect()
    return null;
};
