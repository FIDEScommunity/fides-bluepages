import { CredentialAttribute } from './CredentialAttribute';
import { ValidationPolicyResult } from './ValidationPolicyResult';
import { Did } from './Did';


export interface Credential {
    id: string;
    type: string;
    icon: string;
    displayName: string;
    status: string;
    validationPolicyResults: ValidationPolicyResult[];
    lastUpdated: string;
    attributes: CredentialAttribute[];
    issuerDidId: string;
    issuerDid: Did;
}
