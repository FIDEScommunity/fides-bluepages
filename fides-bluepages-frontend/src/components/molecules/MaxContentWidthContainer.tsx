import React from 'react';

interface MaxContentWidthContainerProps {
    className?: string | undefined;
    style?: React.CSSProperties | undefined;
    children: string | JSX.Element | JSX.Element[];
}

export const MaxContentWidthContainer: React.FC<MaxContentWidthContainerProps> = (props) => {

    return (
        <div className={"flex justify-content-center " + props.className} style={props.style}>
            <div className="maxContentWidth">
                {props.children}
            </div>
        </div>
    );
};

