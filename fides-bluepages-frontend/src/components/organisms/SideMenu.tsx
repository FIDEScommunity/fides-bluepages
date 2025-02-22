import React, { forwardRef, Ref, useRef } from 'react';
import { Sidebar } from 'primereact/sidebar';
import { Ripple } from 'primereact/ripple';
import { StyleClass } from 'primereact/styleclass';
import { Props } from '../atoms';
import { useSelector } from 'react-redux';
import { globalStateSelector, showMenu, useAppDispatch } from '../../state';
import { AboutMenu } from './AboutMenu';

export const SideMenu = forwardRef((props: Props, ref: Ref<any>) => {
    const dispatch = useAppDispatch();

    let globalState = useSelector(globalStateSelector);
    const btnRef2 = useRef<any>(null);


    return (
        <div className="card flex justify-content-center">
            <Sidebar
                visible={globalState.menuOpen}
                onHide={() => dispatch(showMenu(false))}
                content={() => (
                    <div className="min-h-screen flex relative lg:static surface-ground">
                        <div id="app-sidebar-2" className="surface-section h-screen block flex-shrink-0 absolute lg:static left-0 top-0 z-1 border-right-0 surface-border select-none" style={{width: '20rem'}}>
                            {/*<div id="app-sidebar-2" className="surface-section h-screen block flex-shrink-0 absolute lg:static left-0 top-0 z-1 border-right-1 surface-border select-none">*/}
                            <div className="flex flex-column h-full">
                                <div className="flex flex-column p-4  " style={{backgroundColor: '#0a6ef3'}}>
                                    <div className="font-bold text-white mr-2 md:mr-4 text-3xl md:text-6xl ">FIDES.</div>
                                    <div className="text-white ">Blue Pages</div>
                                </div>
                                <div className="overflow-y-auto">

                                    <ul className="list-none p-3 m-0">
                                        <li>
                                            <ul className="list-none p-0 m-0 overflow-hidden">
                                                <li>
                                                    <a href="/" className="p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 hover:surface-100 transition-duration-150 transition-colors w-full">
                                                        <i className="pi pi-search mr-2"></i>
                                                        <span className="font-medium">Search</span>
                                                        <Ripple/>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="/did/add" className="p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 hover:surface-100 transition-duration-150 transition-colors w-full">
                                                        <i className="pi pi-plus mr-2"></i>
                                                        <span className="font-medium">Register organisation</span>
                                                        <Ripple/>
                                                    </a>
                                                </li>


                                                <li>
                                                    <StyleClass nodeRef={btnRef2} selector="@next" enterFromClassName="hidden" enterActiveClassName="slidedown" leaveToClassName="hidden" leaveActiveClassName="slideup">
                                                        <a ref={btnRef2} className="p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 hover:surface-100 transition-duration-150 transition-colors w-full">
                                                            <i className="pi pi-question-circle mr-2"></i>
                                                            <span className="font-medium">About</span>
                                                            <i className="pi pi-chevron-down ml-auto mr-1"></i>
                                                            <Ripple/>
                                                        </a>
                                                    </StyleClass>
                                                    <AboutMenu className="pl-3"/>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                )}
            ></Sidebar>
        </div>
    )
})
