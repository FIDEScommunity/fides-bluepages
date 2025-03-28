import React from 'react';
import { IconProps } from './IconProps';


export const ErrorExclamation: React.FC<IconProps> = ({width = '20', height = "20", className}: IconProps) => {
    return (
        <svg xmlns="http://www.w3.org/2000/svg" width={width} height={height} viewBox="0 0 20 20" fill="none" className={className}>
            <path d="M10 20C4.477 20 0 15.523 0 10C0 4.477 4.477 0 10 0C15.523 0 20 4.477 20 10C20 15.523 15.523 20 10 20ZM9 13V15H11V13H9ZM9 5V11H11V5H9Z" fill="#E53E3E"/>
        </svg>

    )
};
