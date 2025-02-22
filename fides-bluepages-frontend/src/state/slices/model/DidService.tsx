import { Credential } from './Credential';

export interface DidService {
    id: number;
    externalKey: string;
    title: string;
    icon: string;
    serviceId: string;
    serviceType: string;
    serviceTypeLabel: string;
    serviceTypeJson: string;
    serviceEndpoint: string;
    serviceEndpointLabel: string;
    serviceEndpointJson: string;
    credentials: Credential[];
}
