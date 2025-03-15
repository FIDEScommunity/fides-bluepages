import * as React from 'react';
import { FC } from 'react';
import { Credential, Did, DidService } from '../../state';
import { ClipboardCopyElement, OCard, ServiceEndpointFormattedView, TextWithExternalLink, TextWithLabel } from '../molecules';
import { CredentialStatusBadge } from '../molecules/CredentialStatusBadge';
import { Image } from 'primereact/image';
import { Button } from 'primereact/button';
import { useNavigate } from 'react-router-dom';

interface ShowServicesViewProps {
    did?: Did;
    className?: string | undefined;
    rightHeaderElement?: React.ReactNode;
}


export const ShowDidView: FC<ShowServicesViewProps> = (props) => {
    let navigation = useNavigate();

    function showCredentials(credentials: Credential[]) {
        return credentials.map((credential, index) =>
            <OCard className="mb-4"
                   title={credential.displayName}
                   titlePrefix={<i className={"mr-2 pi " + credential.icon}/>}
                   titlePostfix={<CredentialStatusBadge status={credential.status} validationPolicyResult={credential.validationPolicyResults}/>}
                   key={index}>

                {credential.attributes.filter((attribute => attribute.dataType !== 'image'))
                    .map((attribute, index2) => (
                        <TextWithLabel label={attribute.displayName} value={attribute.value} key={index2}/>
                    ))}
                {(credential.issuerDid?.title?.value !== undefined) && (
                    <TextWithLabel className="mt-4" label="Issuer" value={<Button className="p-0 text-color" label={credential.issuerDid.title?.value} link onClick={() => navigation('/did/' + credential.issuerDid.externalKey)}/>}/>
                )}
                {(credential.issuerDid?.title?.value === undefined) && (
                    <TextWithLabel className="mt-4" label="Issuer" value={<ClipboardCopyElement copyValue={credential.issuerDidId}>{credential.issuerDidId}</ClipboardCopyElement>}/>
                )}
            </OCard>
        )
    }

    function showService(service: DidService) {
        if ((service.credentials === undefined) || (service.credentials.length === 0)) {
            return <OCard className="mb-4" title={service.title} titlePrefix={<i className={"mr-2 pi " + service.icon}/>}>
                <TextWithLabel label="Service Id" value={service.serviceId}/>
                {(service.serviceType !== undefined) && (<TextWithLabel label={service.serviceTypeLabel} value={service.serviceType}/>)}
                {(service.serviceTypeJson !== undefined) && (<TextWithLabel label={service.serviceTypeLabel} value={service.serviceTypeJson}/>)}
                {(service.serviceEndpoint !== undefined) && (<TextWithLabel label={service.serviceEndpointLabel} value={<ServiceEndpointFormattedView serviceType={service.serviceType} value={service.serviceEndpoint}/>}/>)}
                {(service.serviceEndpointJson !== undefined) && (<TextWithLabel label={service.serviceEndpointLabel} value={<ServiceEndpointFormattedView serviceType={service.serviceType} value={service.serviceEndpointJson}/>}/>)}
            </OCard>
        } else {
            return showCredentials(service.credentials);
        }
    }

    return (
        <div className={props.className}>
            <div className="flex justify-content-between align-items-start">
                <div className="flex md:align-items-center flex-column md:flex-row">
                    {(props.did?.logo?.value?.length && props.did?.logo?.value?.length > 0) && (
                        <Image src={props.did?.logo?.value} alt="Logo" width="120" className="m-0 mb-2 md:m-2"/>
                    )}
                    <div>
                        <div className="text-3xl">{props.did?.title?.value}</div>
                        <div className="text-xs mt-2 mb-2"><ClipboardCopyElement copyValue={props.did?.did}>{props.did?.did}</ClipboardCopyElement></div>
                        <div className=""><TextWithExternalLink link={props.did?.subTitle1?.value}/></div>
                        <div className="mb-4"><TextWithExternalLink link={props.did?.subTitle2?.value}/></div>
                    </div>
                </div>
                {props.rightHeaderElement}
            </div>
            {props.did?.services?.map((service, index) => (
                <div key={index}>
                    {showService(service)}
                </div>
            ))}
        </div>
    );
}
