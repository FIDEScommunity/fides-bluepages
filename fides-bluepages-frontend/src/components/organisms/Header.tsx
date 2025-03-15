import React from 'react';
import { globalStateSelector, showMenu, useAppDispatch } from '../../state';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { Button } from 'primereact/button';
import { ProgressBar } from 'primereact/progressbar';
import './Header.css'
import { SideMenu } from './SideMenu';

interface Props {
}


export const Header: React.FC<Props> = (props) => {
    const dispatch = useAppDispatch();
    const {t} = useTranslation();
    let navigation = useNavigate();
    let globalState = useSelector(globalStateSelector);


    return (
        <div>
            <SideMenu/>
            <div className="col-12 p-0" style={{minHeight: '140px'}}>
                <div className="pl-3 md:pl-6 pr-3 md:pr-6 flex align-items-center justify-content-between" style={{minHeight: '140px', backgroundColor: '#0a6ef3'}}>
                    <div className="flex align-items-baseline">
                        <a href="https://fides.community" className="font-bold text-white mr-2 md:mr-4 text-3xl md:text-6xl ">FIDES.</a>
                        <a className="text-xl md:text-4xl text-white cursor-pointer" onClick={event => {
                            navigation('/')
                        }}>Blue Pages</a>
                    </div>
                    <div className="flex flex-end">
                        <div className="mr-2 hidden md:block"><Button label={t('screens.header.registerOrganization')} link onClick={() => navigation('/did/add')}/></div>
                        <div className="mr-2 hidden md:block"><Button label={t('screens.header.about')} link onClick={() => navigation('/about/introduction')}/></div>
                        <div className="mr-2 block md:hidden"><Button icon="pi pi-bars" link onClick={() => {
                            dispatch(showMenu(true))
                        }}/></div>
                    </div>
                </div>
                <div style={{borderBottom: 'solid 2px #0a6ef3'}}>
                    <ProgressBar mode={globalState.isLoading ? "indeterminate" : "determinate"} value="100" showValue={false} color={'#0a6ef3'} style={{height: '2px', backgroundColor: '#c8c8c8'}}></ProgressBar>
                </div>
            </div>
        </div>
    );

};
