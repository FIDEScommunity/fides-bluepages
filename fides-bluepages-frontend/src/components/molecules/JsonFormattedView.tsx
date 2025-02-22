import React, { useMemo } from 'react';
import { TextWithExternalLink } from './TextWithExternalLink';
import JsonView from '@uiw/react-json-view';


export interface JsonFormattedViewProps {
    value?: string | undefined;
    className?: string | undefined;
}

export const JsonFormattedView: React.FC<JsonFormattedViewProps> = (props) => {

    const links = useMemo(() => {
        if (props.value === undefined) {
            return;
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
        if (json['origin'] !== undefined) {
            return arrayToLinks(json['origin']);
        }
        return arrayToLinks(json);
    }, [props.value]);

    const valueObj = useMemo(() => {
        if (props.value === undefined) {
            return {};
        }
        return JSON.parse(props.value!);
    }, []);
    return (
        <div className="flex flex-column">
            {(links && (links.length > 0) && links.map((link) => <div><TextWithExternalLink link={link}/></div>))}
            {(links && (links.length === 0) && (<JsonView value={valueObj}/>))}
        </div>
    );

};

