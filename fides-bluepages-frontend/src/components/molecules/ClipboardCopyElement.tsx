import * as React from 'react';
import { FC, PropsWithChildren } from 'react';
import { useTranslation } from 'react-i18next';
import { useAppDispatch } from '../../state';
import { setToastMessage } from '../../state/slices/toast';


export interface ClipboardCopyElementProps {
    copyValue?: string;
    className?: string | undefined;
}

export const ClipboardCopyElement: FC<ClipboardCopyElementProps & PropsWithChildren> = (props) => {
    const {t} = useTranslation();
    const dispatch = useAppDispatch();

    function copyToClipboard() {
        navigator.clipboard.writeText('' + props.copyValue);
        dispatch(setToastMessage({message: t('generic.copiedToClipboard')}));
    }

    return (
        <div>{props.children}<i className="pi pi-copy text-500 ml-1" onClick={event => {
            copyToClipboard();
        }}></i></div>
    );
}
