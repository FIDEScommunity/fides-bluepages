import React, { useMemo } from 'react';
import { TextWithExternalLink } from './TextWithExternalLink';


export interface ServiceEndpointOID4VCIViewProps {
    value?: string | undefined;
    className?: string | undefined;
}

export const ServiceEndpointFormattedOID4VCIView: React.FC<ServiceEndpointOID4VCIViewProps> = (props) => {

    const link = useMemo(() => {
        if (props.value === undefined) {
            return;
        }
        if ((props.value.startsWith('http')) && (!props.value.endsWith('.well-known/openid-credential-issuer'))) {
            return props.value + (props.value.endsWith('/') ? '' : '/') + '.well-known/openid-credential-issuer';
        }
        return props.value;
    }, [props.value]);


    return (
        <TextWithExternalLink label={props.value} link={link}/>
    )
};

