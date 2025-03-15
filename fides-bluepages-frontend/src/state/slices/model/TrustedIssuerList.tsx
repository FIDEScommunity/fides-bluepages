import { Did } from './Did';

export interface TrustedIssuerList {
    credentialType: string;
    trustedDids: Did[];
}
