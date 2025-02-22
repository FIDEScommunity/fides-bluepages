import React, { useMemo } from 'react';
import { ValidationPolicyResult } from '../../state';
import { Tooltip } from 'primereact/tooltip';
import { v4 as uuidv4 } from 'uuid';


export interface CredentialStatusBadgeProps {
    status: string;
    validationPolicyResult: ValidationPolicyResult[];
    className?: string | undefined;
}

export const CredentialStatusBadge: React.FC<CredentialStatusBadgeProps> = (props) => {

    const controlName = useMemo(() => (uuidv4()), []);

    function getColor() {
        switch (props.status) {
            case 'UNCHECKED':
                return '#a8a8a8';
            case 'VALID':
                return '#38A169';
            case 'INVALID':
                return '#000000';
            case 'EXPIRED':
                return '#ff0000';

        }
    }


    function getIcon(isValid: boolean) {
        if (isValid) {
            return <i className="pi pi-check-circle text-green-500 mr-2"/>
        } else {
            return <i className="pi pi-times-circle text-red-500 mr-2"/>
        }
    }

    return (
        <>
            <Tooltip target={".custom-tooltip-btn" + controlName}>
                <div className="p-1 md:p-4">
                    <div className="pt-2 pb-2 font-bold">Validation checks</div>
                    {props.validationPolicyResult?.map((result, index) => (
                        <div key={index} className="pt-2 pb-2">
                            <div className="font-bold">{getIcon(result.valid)}{result.policyName}</div>
                            <div className="text-sm">{result.policyDescription}</div>
                        </div>
                    ))}
                </div>
            </Tooltip>

            <div className={"p-1 text-white font-bold custom-tooltip-btn" + controlName} data-pr-position="left" style={{borderRadius: '4px', backgroundColor: getColor()}}>{props.status}</div>
        </>
    );
};

