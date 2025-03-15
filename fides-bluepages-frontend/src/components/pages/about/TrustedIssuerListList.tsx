import React, { FC, useEffect, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { DataTable } from 'primereact/datatable';
import { useSelector } from 'react-redux';
import { getTrustedIssuerList, trustedIssuerListSelector } from '../../../state/slices/trustedissuerlist';
import { Did, TrustedIssuerList, useAppDispatch } from '../../../state';
import { Column } from 'primereact/column';
import { ClipboardCopyElement } from '../../molecules';
import { Button } from 'primereact/button';
import { useNavigate } from 'react-router-dom';

export const TrustedIssuerListList: FC = () => {
    const dispatch = useAppDispatch();
    const navigation = useNavigate();
    const {t} = useTranslation();

    useEffect(() => {
        dispatch(getTrustedIssuerList({}));
    }, []);


    let trustedIssuerListState = useSelector(trustedIssuerListSelector);

    const flattenedDids = useMemo(() => {
        function getDids(trustedIssuer: TrustedIssuerList): { credentialType: string; did: Did; }[] {
            return trustedIssuer.trustedDids.flatMap(did => {
                return {credentialType: trustedIssuer.credentialType, did}
            });
        }

        return trustedIssuerListState.list.flatMap(trustedIssuer => getDids(trustedIssuer));
    }, [trustedIssuerListState.list]);


    const showIssuer = (flattenedDid: { credentialType: string; did: Did; }) => {
        return (<Button className="p-0 text-color" label={flattenedDid.did.title?.value} link onClick={() => navigation('/did/' + flattenedDid.did.externalKey)}/>)
    }
    const showDid = (flattenedDid: { credentialType: string; did: Did; }) => {
        return (<ClipboardCopyElement copyValue={flattenedDid.did.did}>{flattenedDid.did.did}</ClipboardCopyElement>)
    }
    return (
        <div>
            <div className="flex flex-column">
                <h1 className="font-bold text-xl mt-3 mb-1">{t('screens.trustedIssuersList.title')}</h1>
                <p>{t('screens.trustedIssuersList.subTitle')}</p>
            </div>

            <DataTable value={flattenedDids} tableStyle={{minWidth: '50rem'}}>
                <Column field="credentialType" header="Credential type"></Column>
                <Column header="Issuer" body={showIssuer}></Column>
                <Column header="Identifier" body={showDid}></Column>
            </DataTable>
        </div>
    );
};
