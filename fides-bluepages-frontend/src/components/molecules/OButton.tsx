import React, { PropsWithChildren } from 'react';
import { Button, ButtonProps } from 'primereact/button';
import { IconType } from 'primereact/utils';

interface OButtonProps {
    className?: string | undefined;
    label: string;
    icon?: IconType<ButtonProps> | undefined;
    onClick?: () => void;
    disabled?: boolean;
    severity?: 'secondary' | 'success' | 'info' | 'warning' | 'danger' | 'help' | 'contrast' | undefined;
}

export const OButton: React.FC<OButtonProps & PropsWithChildren> = (props) => {

    return (
        <Button label={props.label}
                className={props.className}
                icon={props.icon}
                severity={props.severity}
                style={{lineHeight: '1rem'}}
                onClick={props.onClick}
                disabled={props.disabled}/>
    );
};
