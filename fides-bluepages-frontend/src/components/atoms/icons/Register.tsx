import React from 'react';
import { IconProps } from './IconProps';


export const Register: React.FC<IconProps> = ({width = '20', height = "20", className}: IconProps) => {
    return (
        <svg xmlns="http://www.w3.org/2000/svg" width={width} height={height} viewBox="0 0 12 12" fill="none" className={className}>
            <path
                d="M4.66666 0.666626H7.33332V3.33329H4.66666V0.666626ZM0.666656 8.66663H3.33332V11.3333H0.666656V8.66663ZM0.666656 4.66663H3.33332V7.33329H0.666656V4.66663ZM0.666656 0.666626H3.33332V3.33329H0.666656V0.666626ZM7.33332 6.27996V4.66663H4.66666V7.33329H6.27999L7.33332 6.27996ZM11.92 5.52663L11.14 4.74663C11.1146 4.72123 11.0845 4.70108 11.0513 4.68733C11.0181 4.67359 10.9826 4.66651 10.9467 4.66651C10.9107 4.66651 10.8752 4.67359 10.842 4.68733C10.8088 4.70108 10.7787 4.72123 10.7533 4.74663L10.1667 5.33329L11.3333 6.49996L11.92 5.91329C11.9454 5.88791 11.9655 5.85777 11.9793 5.8246C11.993 5.79142 12.0001 5.75587 12.0001 5.71996C12.0001 5.68405 11.993 5.64849 11.9793 5.61532C11.9655 5.58215 11.9454 5.55201 11.92 5.52663ZM5.33332 10.1666V11.3333H6.49999L10.9467 6.88663L9.77999 5.71996L5.33332 10.1666ZM8.66666 0.666626H11.3333V3.33329H8.66666V0.666626Z"
                fill="white"/>
        </svg>
    )
};
