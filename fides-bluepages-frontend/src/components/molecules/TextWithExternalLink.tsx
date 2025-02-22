import React from 'react';

interface TextWithExternalLinkProps {
    className?: string | undefined;
    label?: string;
    link?: string;
}

export const TextWithExternalLink: React.FC<TextWithExternalLinkProps> = (props) => {

    if (!props.link) {
        return null;
    }

    function isUrl(value: string) {
        if (typeof value !== 'string') {
            return false;
        }
        return (value.startsWith('http://') || value.startsWith('https://'));
    }

    if (!isUrl(props.link)) {
        return props.label ? <span className={props.className}>{props.label}</span> : <span className={props.className}>{props.link}</span>;
    }
    return (
        <span className={props.className}>
            <a href={props.link} target="_blank" rel="noreferrer">{(props.label === undefined) ? props.link : props.label}
                <span className="ml-1 text-xs pi pi-external-link"/>
            </a>
        </span>
    );
};


