import React, { useMemo } from 'react';
import { TextWithExternalLink } from './TextWithExternalLink';
import JsonView from '@uiw/react-json-view';
import { ServiceEndpointFormattedOID4VCIView } from './ServiceEndpointFormattedOID4VCIView';


export interface ServiceEndpointViewProps {
    value?: string | undefined;
    serviceType: string | undefined;
    className?: string | undefined;
}

export const ServiceEndpointFormattedView: React.FC<ServiceEndpointViewProps> = (props) => {

    function isJsonString(str: string) {
        try {
            JSON.parse(str);
        } catch (e) {
            return false;
        }
        return true;
    }


    const links = useMemo(() => {
        if (props.value === undefined) {
            return;
        }
        if (!isJsonString(props.value)) {
            return (props.value.startsWith('http')) ? [props.value] : [];
        }

        function arrayToLinks(jsonElement: any): string[] {
            if (Array.isArray(jsonElement)) {
                return jsonElement.flatMap(arrayToLinks)
                    .filter((element) => typeof element === 'string' && element.startsWith('http'));
            } else if (typeof jsonElement === 'string' && jsonElement !== null && jsonElement.startsWith('http')) {
                return [jsonElement];
            }
            return jsonElement as string[];
        }

        let json = JSON.parse(props.value!);
        if (json['origins'] !== undefined) {
            return arrayToLinks(json['origins']);
        }
        return arrayToLinks(json);
    }, [props.value]);


    const valueJson = useMemo(() => {
        if ((props.value === undefined) || !isJsonString(props.value)) {
            return undefined;
        }
        return JSON.parse(props.value!);
    }, [props.value]);

    if (props.value === undefined) {
        return null;
    }

    if (props.serviceType === 'OID4VCI') {
        return <ServiceEndpointFormattedOID4VCIView value={props.value} className={props.className}/>;
    } else {
        return (
            <div className="flex flex-column">
                {(links && (links.length > 0) && links.map((link, index) => <div key={index}><TextWithExternalLink link={link}/></div>))}
                {(links && (links.length === 0) && (valueJson !== undefined) && (<JsonView value={valueJson}/>))}
                {(links && (links.length === 0) && (valueJson === undefined) && (<div>{props.value}</div>))}
            </div>
        )
    }
};

