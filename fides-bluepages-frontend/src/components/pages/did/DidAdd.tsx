import * as React from 'react';
import { FC, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { didSelector, getDidPreview, useAppDispatch } from '../../../state';
import { userPreferenceSelector } from '../../../state/slices/userpreference';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { BackLink, MaxContentWidthContainer, OCard, ReadMorePanel, TextInput, TextToHtml } from '../../molecules';
import { Button } from 'primereact/button';
import { Register } from '../../atoms';
import { ErrorAlert } from '../../molecules/ErrorAlert';

export const DidAdd: FC = () => {
    const dispatch = useAppDispatch();
    const navigate = useNavigate();
    const {t} = useTranslation();
    const [did, setDid] = useState<string>('');
    let userPreference = useSelector(userPreferenceSelector).singleItem;

    function register() {
        if (did === undefined) {
        }
        dispatch(getDidPreview({did: did, locale: userPreference?.locale, performValidations: false})).then((response) => {
            if (response.type.includes('fulfilled')) {
                navigate('/did/add/preview/' + encodeURIComponent(did))
            }
        });

    }

    let didState = useSelector(didSelector);

    const companyExampleJson = {
        "legalName": "Credenco B.V.",
        "url": "https://www.credenco.com",
        "telephone": "+31 85 820 97 25",
        "email": "info@credenco.com",
        "location": {
            "address": {
                "postalCode": "2411 PV",
                "streetAddress": "Tolnasingel 3",
                "addressLocality": "Bodegraven, The Netherlands",
            },
            "geo": {
                "latitude": "52.0682894",
                "longitude": "4.762281"
            }
        }
    };

    return (
        <MaxContentWidthContainer className="mt-2">
            <div>
                <BackLink label="Back" onClick={() => navigate('/did')} className="mb-4"/>
                <OCard className="p-3 md:p-3 lg:p-6">
                    <div className="font-bold text-xl mb-1">{t('screens.addDid.title')}</div>
                    <div className="mb-4">{t('screens.addDid.subTitle')}</div>
                    <div className="mb-2">{t('screens.addDid.didLabel')}</div>
                    <TextInput className="" value={did} onChangeValue={(value) => setDid('' + value)} placeHolder={t('screens.addDid.didInputPlaceholder')}
                               onKeyUp={(e) => {
                                   if (e.key === 'Enter') {
                                       register();
                                   }
                               }}
                    />
                    {(didState.error !== undefined && did.length > 0) && (<div className="mt-1 mb-2 text-sm p-error">{t('screens.addDid.didNotFoundTryAgain')}</div>)}
                    <ErrorAlert className=""
                                errorTitle={t('screens.addDid.didNotFoundErrorTitle')}
                                errorMessage={t('screens.addDid.didNotFoundErrorBody')}
                                show={didState.error !== undefined && did.length > 0}/>
                    <Button className="mt-4" label={t('screens.addDid.registerButtonLabel')}
                            icon={<Register className="mr-2"/>}
                            onClick={register}/>
                </OCard>
                <div className="mt-8">
                    <div className="font-bold mb-2">{t('screens.addDid.schemaInfo.short.title')}</div>
                    <ReadMorePanel bodyPart1={
                        <TextToHtml value={t('screens.addDid.schemaInfo.short.body')}/>
                    }
                                   bodyPart2={
                                       <div>
                                           <TextToHtml value={t('screens.addDid.schemaInfo.long.body')}/>
                                           <pre>{JSON.stringify(companyExampleJson, null, 2)}
                            </pre>
                                       </div>
                                   }
                    />


                </div>
            </div>
        </MaxContentWidthContainer>
    )
}
