import React, { FC } from 'react';
import { Ripple } from 'primereact/ripple';


export interface AboutMenuProps {
    className?: string;
}

export const AboutMenu: FC<AboutMenuProps> = (props) => {

    return (

        <ul className={props.className + " list-none py-0 pr-0 m-0 overflow-y-hidden transition-all transition-duration-400 transition-ease-in-out "}>
            <li>
                <a className="p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 hover:surface-100 transition-duration-150 transition-colors w-full"
                   href="/about/introduction">
                    <i className="pi pi-book mr-2"></i>
                    <span className="font-medium">Introduction</span>
                    <Ripple/>
                </a>
            </li>
            <li>
                <a className="p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 hover:surface-100 transition-duration-150 transition-colors w-full"
                   href="/about/architecture">
                    <i className="pi pi-objects-column mr-2"></i>
                    <span className="font-medium">Architecture</span>
                    <Ripple/>
                </a>
            </li>
            <li>
                <a className="p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 hover:surface-100 transition-duration-150 transition-colors w-full"
                   href="/about/trustedIssuers">
                    <i className="pi pi-home mr-2"></i>
                    <span className="font-medium">Trusted Issuers</span>
                    <Ripple/>
                </a>
            </li>
            <li>
                <a className="p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 hover:surface-100 transition-duration-150 transition-colors w-full"
                   href="/api/public/swagger-ui">
                    <i className="pi pi-bookmark mr-2"></i>
                    <span className="font-medium">API documentation</span>
                    <Ripple/>
                </a>
            </li>
            <li>
                <a className="p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 hover:surface-100 transition-duration-150 transition-colors w-full"
                   href="https://github.com/FIDEScommunity/fides-bluepages">
                    <i className="pi pi-bookmark mr-2"></i>
                    <span className="font-medium">Source code</span>
                    <Ripple/>
                </a>
            </li>

        </ul>


    );
};
