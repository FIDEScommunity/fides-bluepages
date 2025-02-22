import * as React from 'react';
import { FC, useEffect } from 'react';
import { didSelector, getDid, getDidValidations, useAppDispatch } from '../../../state';
import { useAuth } from 'react-oidc-context';
import { userPreferenceSelector } from '../../../state/slices/userpreference';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { BackLink, MaxContentWidthContainer } from '../../molecules';
import { Loading } from '../Loading';
import { ShowDidView } from '../../organisms';

export const DidDetail: FC = () => {
    const dispatch = useAppDispatch();
    const auth = useAuth();
    const navigate = useNavigate();

    const {externalKey} = useParams();
    let userPreference = useSelector(userPreferenceSelector).singleItem;

    let did = useSelector(didSelector);

    useEffect(() => {
        dispatch(getDid({
            jwtToken: auth.user?.access_token,
            locale: userPreference?.locale,
            externalKey: externalKey
        })).then((response) => {
            if (response.type.includes('fulfilled')) {
                dispatch(getDidValidations({
                    jwtToken: auth.user?.access_token,
                    locale: userPreference?.locale,
                    externalKey: externalKey
                }))
            }
        });
    }, [auth.user?.access_token, externalKey, userPreference?.locale]);

    if (did.singleItem === undefined) {
        return <Loading></Loading>;
    }

    return (
        <MaxContentWidthContainer className="mt-2">
            <div>
                <BackLink label="Back" onClick={() => navigate('/did')} className="mb-4"/>
                <ShowDidView did={did.singleItem}/>
            </div>
        </MaxContentWidthContainer>
    );
}

