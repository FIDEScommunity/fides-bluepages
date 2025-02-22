import React from 'react';
import { Button } from 'primereact/button';


export interface BackLinkProps {
    className?: string | undefined;
    label: string;
    onClick?: () => void;
}

export const BackLink: React.FC<BackLinkProps> = (props) => {

    return (
        <div className={"flex align-items-center text-primary font-bold " + props.className} onClick={props.onClick}>
            <i className="pi pi-arrow-left"/>
            <Button className="text-primary font-bold p-0 pl-2" label={props.label} link/>
        </div>
    );
};

