import React, { FC, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { DataTable } from 'primereact/datatable';
import { useSelector } from 'react-redux';
import { getTrustedIssuerList, trustedIssuerListSelector } from '../../../state/slices/trustedissuerlist';
import { TrustedIssuerList, useAppDispatch } from '../../../state';
import { Column } from 'primereact/column';
import { ClipboardCopyElement } from '../../molecules';

export const TrustedIssuerListList: FC = () => {
    const dispatch = useAppDispatch();
    const {t} = useTranslation();

    useEffect(() => {
        dispatch(getTrustedIssuerList({}));
    }, []);


    let trustedIssuerListState = useSelector(trustedIssuerListSelector);

    const showDids = (trustedIssuer: TrustedIssuerList) => {
        if ((trustedIssuer.trustedDids === undefined) || (trustedIssuer.trustedDids.length === 0)) {
            return (<div>-</div>)
        }
        return (<div>
            {trustedIssuer.trustedDids.map(did => {
                return (<ClipboardCopyElement copyValue={did.did}>{did.did}</ClipboardCopyElement>)
            })
            }
        </div>)
    }
    return (
        <div>
            <div className="flex flex-column">
                <h1 className="font-bold text-xl mt-3 mb-1">{t('screens.trustedIssuersList.title')}</h1>
                <p>{t('screens.trustedIssuersList.subTitle')}</p>
            </div>

            <DataTable value={trustedIssuerListState.list} tableStyle={{minWidth: '50rem'}}>
                <Column field="credentialType" header="Credential type"></Column>
                <Column header="Trusted dids" body={showDids}></Column>
            </DataTable>
        </div>
    );
};
