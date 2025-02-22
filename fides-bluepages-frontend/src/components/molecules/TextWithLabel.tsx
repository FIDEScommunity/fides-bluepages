import React, { useMemo } from 'react';
import { InputWithLabel } from './InputWithLabel';
import { TextWithExternalLink } from './TextWithExternalLink';


export interface TextWithLabelProps {
    label: string | React.ReactNode;
    value?: string | React.ReactNode | undefined;
    className?: string | undefined;
    footer?: React.ReactNode | undefined;
    postElement?: React.ReactNode | undefined;
    classNameLabel?: string | undefined;
    classNameValue?: string | undefined;
}

export const TextWithLabel: React.FC<TextWithLabelProps> = (props) => {

    const label = useMemo(() => {
        if (typeof props.label === 'string') {
            return <TextWithExternalLink link={props.label}/>
        }
        return props.label;
    }, [props.label]);

    const value = useMemo(() => {
        if (typeof props.value === 'string') {
            return <TextWithExternalLink link={props.value}/>
        }
        return props.value;
    }, [props.value]);
    return (
        <InputWithLabel className={props.className} label={label} inputElement={value} footer={props.footer} postElement={props.postElement} classNameLabel={props.classNameLabel} classNameValue={props.classNameValue}/>
    );
};

