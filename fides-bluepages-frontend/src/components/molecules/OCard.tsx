import React, { PropsWithChildren } from 'react';

interface CardProps {
    className?: string | undefined;
    style?: React.CSSProperties | undefined;
    title?: string | React.ReactNode | undefined;
    titlePrefix?: React.ReactNode | undefined;
    titlePostfix?: React.ReactNode | undefined;
}

export const OCard: React.FC<CardProps & PropsWithChildren> = (props) => {
    return (
        <div className={props.className} style={Object.assign({paddingTop: 24, paddingBottom: 24, backgroundColor: '#F7FAFC', borderRadius: 8, padding: 16, borderStyle: 'solid', borderWidth: '1px', borderColor: '#E2E8F0'}, props.style)}>
            {(props.titlePrefix || props.title || props.titlePostfix) && (
                <div className="flex justify-content-between align-items-start">
                    <div className="flex pb-4 align-items-center text-lg">
                        {(props.titlePrefix) && (<>{props.titlePrefix}</>)}
                        {(props.title) && (<div className="font-semibold font-">{props.title}</div>)}
                    </div>
                    {(props.titlePostfix) && (<>{props.titlePostfix}</>)}
                </div>
            )}
            {props.children}
        </div>
    );
};

