import * as React from 'react';
import { FC, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { addDid, didSelector, getDidPreview, useAppDispatch } from '../../../state';
import { userPreferenceSelector } from '../../../state/slices/userpreference';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { BackLink, MaxContentWidthContainer, OButton } from '../../molecules';
import { Loading } from '../Loading';
import { ShowDidView } from '../../organisms';
import { Register } from '../../atoms';
import { ErrorAlert } from '../../molecules/ErrorAlert';

export const DidAddPreview: FC = () => {
    const dispatch = useAppDispatch();
    const navigate = useNavigate();
    const {did} = useParams();
    const {t} = useTranslation();

    let userPreference = useSelector(userPreferenceSelector).singleItem;

    let didState = useSelector(didSelector);

    useEffect(() => {
            dispatch(getDidPreview({
                locale: userPreference?.locale,
                did: did,
                performValidations: true,
            }));
    }, [did, userPreference?.locale]);

    if (didState.singleItem === undefined) {
        return <Loading></Loading>;
    }


    function add() {
        dispatch(addDid({did: did, locale: userPreference?.locale})).then((response) => {
            if (response.type.includes('fulfilled')) {
                navigate('/did/add/completed')
            }
        });
    }

    return (
        <MaxContentWidthContainer className="mt-2">
            <div>
                <BackLink label="Back" onClick={() => navigate('/did/add')} className="mb-4"/>
                <ShowDidView did={didState.singleItem} rightHeaderElement={
                    <OButton className="mb-2" label={t('screens.addDidPreview.addButtonLabel')}
                             icon={<Register className="mr-2" height="16" width="16"/>}
                             onClick={add}/>
                }/>
            </div>
            <ErrorAlert className="mb-4"
                        errorTitle={t('screens.addDidPreview.addDidFailedErrorTitle')}
                        errorMessage={t('screens.addDidPreview.addDidFailedErrorBody')}
                        show={didState.error !== undefined}/>

            <OButton label={t('screens.addDidPreview.addButtonLabel')}
                     icon={<Register className="mr-2" height="16" width="16"/>}
                     onClick={add}/>
            <OButton label={t('screens.addDidPreview.returnToOverview')}
                     className="ml-2"
                     severity={'secondary'}
                     onClick={() => navigate('/div')}/>

        </MaxContentWidthContainer>
    );
}

