export interface TrustedIssuerListDid {
    did: string;
}

export interface TrustedIssuerList {
    credentialType: string;
    trustedDids: TrustedIssuerListDid[];
}
