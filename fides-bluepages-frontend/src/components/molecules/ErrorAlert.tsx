import React, { FC } from 'react';
import { Message } from 'primereact/message';
import { ErrorExclamation } from '../atoms';

interface Props {
    errorTitle?: string
    errorMessage?: string
    show: boolean
    className?: string;
}

export const ErrorAlert: FC<Props> = (props) => {
    if (!props.show) {
        return null;
    }
    return (
        <Message icon={<ErrorExclamation className="mr-3" width="36" height="36"/>} severity="error" className={"p-4 border-primary justify-content-start w-full " + props.className} text={
            <div>
                {props.errorTitle && <div className="font-bold">{props.errorTitle}</div>}
                {props.errorMessage && <div>{props.errorMessage}</div>}
            </div>
        }/>
    )
};
