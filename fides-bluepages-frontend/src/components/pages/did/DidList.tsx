import * as React from 'react';
import { FC, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { didSelector, getDids, useAppDispatch } from '../../../state';
import { useAuth } from 'react-oidc-context';
import { DataViewPageEvent } from 'primereact/dataview';
import { getUserPreferenceWithDefault, userPreferenceSelector } from '../../../state/slices/userpreference';
import { useSelector } from 'react-redux';
import { PaginatorPageChangeEvent } from 'primereact/paginator';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { useNavigate } from 'react-router-dom';
import { Did } from '../../../state/slices/model/Did';
import { OPaginator, SearchEntry } from '../../molecules';
import backgroundPatternSvg from '../../atoms/background-pattern.svg';

export const DidList: FC = () => {
    const dispatch = useAppDispatch();
    const auth = useAuth();
    const navigate = useNavigate();
    const {t} = useTranslation();
    const [searchValue, setSearchValue] = useState<string>();

    let userPreference = useSelector(userPreferenceSelector).singleItem;
    let dids = useSelector(didSelector);

    const initialParams: DataViewPageEvent = {
        first: 0,
        rows: Number(getUserPreferenceWithDefault(userPreference, 'didSearchList.pageSize', "30")),
        page: 0,
        pageCount: 1
    };
    const [tableParams, setTableParams] = useState<DataViewPageEvent>(initialParams);

    useEffect(() => {
        setTableParams({...tableParams, pageCount: dids.totalPages});
    }, [dids.totalPages]);

    function onPageChange(paginatorPageChangeEvent: PaginatorPageChangeEvent) {
        setTableParams({...tableParams, page: paginatorPageChangeEvent.page, rows: paginatorPageChangeEvent.rows});
    }

    function onSearch(q: string | undefined) {
        setSearchValue(q);
        dispatch(getDids({
            jwtToken: auth.user?.access_token,
            locale: userPreference?.locale,
            page: tableParams.page,
            pageSize: (tableParams.rows),
            q: q
        }));

    }

    function onDidClicked(did: Did) {
        navigate('/did/' + did.externalKey);
    }

    function getLogo(logo: string | undefined): React.ReactNode {
        return (
            <div className="flex justify-content-center align-items-center border-1 border-round-md p-2" style={{backgroundColor: '#F7FAFC', borderColor: '#EDF2F7'}}>
                {(logo !== undefined) && (<img src={logo} alt="Logo" height="40" className="" onError={({currentTarget}) => {
                    currentTarget.onerror = null; // prevents looping
                    currentTarget.src = '/blank.png'
                }}/>)}
                {(logo === undefined) && (<i className="pi pi-image" style={{color: '#bdbdbd', fontSize: '20px'}}></i>)}
            </div>
        )
    }

    return <>
        <div
            style={{
                marginTop: -20,
                marginRight: -20,
                marginLeft: -20,
                marginBottom: 20,
                background: "linear-gradient(90deg, #F7FAFC 0%, #F7FAFC 100%)",
                // background: "linear-gradient(90deg, #F7FAFC 99.99%, #A0AEC0 100%);",
                // backgroundSize: "100% 100%"
            }}>
            <div className="flex justify-content-center"
                 style={{
                     paddingTop: 50,
                     paddingBottom: 50,
                     paddingRight: 20,
                     paddingLeft: 20,
                     minHeight: 209,
                     backgroundSize: "100vw 209px",
                     backgroundImage: `url(${backgroundPatternSvg})`
                 }}>
                <div className="maxContentWidth">
                    <div className="font-bold">{t('screens.searchDid.intro.title')}</div>
                    <div>{t('screens.searchDid.intro.subTitle')}</div>
                </div>
            </div>
        </div>
        <div className="flex justify-content-center">
            <div className="maxContentWidth">
                <SearchEntry style={{marginTop: '-45px', marginBottom: 20}} placeHolder={t('screens.searchDid.search.placeholder')}
                             onSearch={value => {
                                 onSearch(value);
                             }}/>
                {(dids.list.length > 0) && (
                    <DataTable value={dids.list} header={<></>} headerColumnGroup={<></>} selectionMode="single"
                               onSelectionChange={(e) => {
                                   onDidClicked(e.value);
                               }}>
                        <Column className="hidden md:block" body={(did: Did) => getLogo(did.logo?.value)}></Column>
                        <Column body={(did: Did) => (did.title === undefined) ? <div>{did.did}</div> : <div>{did.title.value}</div>}></Column>
                        <Column body={(did: Did) => (<div>{did.subTitle1?.value}</div>)}></Column>
                        {/*<Column body={(did: Did) => (<div>{did.subTitle2?.value}</div>)}></Column>*/}
                    </DataTable>
                )}
                {((dids.list.length > 0) && (dids.totalPages > 1)) && (
                    <div className="flex justify-content-end">
                        <OPaginator first={dids.currentPage * tableParams.rows} rows={tableParams.rows} totalRecords={dids.totalElements} onPageChange={onPageChange} userPreferencesKey={"didSearchList"}/>
                    </div>
                )}
            </div>
        </div>
    </>
        ;
}
