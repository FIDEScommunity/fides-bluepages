import React from 'react';

export interface AppTexts {
    appName: {
        displayName: string;
        name: string;
    };
    busyInitializing: string;
    menu: {
        issuanceConfig: string;
        apiDocs: string;
        credentialTypes: string;
        userMaintenance: string;
        organizationMaintenance: string;
        logoff: string;
    };
    generic: {
        yes: string;
        no: string;
        ok: string;
        cancel: string;
        add: string;
        save: string;
        delete: string;
        edit: string;
        back: string;
        loading: string;
        accept: string;
        reject: string;
        search: string;
        startSearching: string;
        removeCredential: string;
        copiedToClipboard: string;
    };
    error: {
        retrievingData: string;
        errorCodes: {
            "ERR-1": string;
        };
    };
    fields: {};
    screens: {
        header: {
            welcomeTitle: string;
            welcomeSubTitle: string;
            registerOrganization: string;
            about: string;
        },
        searchDid: {
            intro: {
                title: string;
                subTitle: string;
            }
            search: {
                label: string;
                placeholder: string;
            }
            noResults: string;
        },
        addDid: {
            title: string;
            subTitle: string;
            didLabel: string;
            didInputPlaceholder: string;
            registerButtonLabel: string;
            didNotFoundTryAgain: string;
            didNotFoundErrorTitle: string;
            didNotFoundErrorBody: string;
            schemaInfo: {
                short: {
                    title: string;
                    body: string;
                },
                long: {
                    title: string;
                    body: string;
                    example: string;
                }
            };
        },
        addDidPreview: {
            addButtonLabel: string;
            returnToOverview: string;
            addDidFailedErrorTitle: string;
            addDidFailedErrorBody: string;
        },
        addDidCompleted: {
            title: string;
            subTitle: string;
            addButtonLabel: string;
            returnToOverview: string;
        },
        trustedIssuersList: {
            title: string;
            subTitle: string;
        },
        introduction: {
            title: string;
            intro: string;
        },
        architecture: {
            title: string;
            subTitle: string;
            description: string;
        },
        eInvoice: {
            title: string;
            overview: {
                title: string;
                description: string;
            },
            scenarioDescription: {
                title: string;
                description: string;
            },
            processFlowDiagram: {
                title: string;
                description: string;
            },
            detailedProcessFlow: {
                title: string;
                step1: string;
                step2: string;
                step3: string;
                step4: string;
                step5: string;
                step6: string;
                step7: string;
                step8: string;
                step9: string;
                step10: string;
                step11: string;
                step12: string;
                step13: string;
                step14: string;
                step15: string;
                step16: string;
                step17: string;
                step18: string;
                step19: string;
                step20: string;
                step21: string;
                step22: string;
                step23: string;
                step24: string;
                step25: string;
                step26: string;
                step27: string;
                step28: string;
                step29: string;
            },
            benefits: {
                title: string;
                benefit1: string;
                benefit2: string;
                benefit3: string;
                benefit4: string;
                benefit5: string;
                benefit6: string;
                benefit7: string;
            },
            technicalRequirements: {
                title: string;
                description: string;
                requirement1: string;
                requirement2: string;
                requirement3: string;
                requirement4: string;
                requirement5: string;
                requirement6: string;
            },
            conclusion: {
                title: string;
                description: string;
            },
        },
    }
}

const getAppTextsEn = (): AppTexts => {
    return {
        menu: {
            credentialTypes: 'Credential Catalog',
            issuanceConfig: 'Issuer configurations',
            userMaintenance: 'Users',
            organizationMaintenance: 'Organizations',
            apiDocs: 'API Docs',
            logoff: 'Logoff'
        },
        generic: {
            yes: 'Ja',
            no: 'Nee',
            ok: 'OK',
            cancel: 'Cancel',
            add: 'Add',
            save: 'Save',
            edit: 'Edit',
            delete: 'Delete',
            back: 'Back',
            loading: 'Loading...',
            accept: 'Accept',
            reject: 'Reject',
            search: 'Search',
            startSearching: 'Start searching...',
            removeCredential: 'Remove credential',
            copiedToClipboard: 'Copied to clipboard'
        },
        error: {
            retrievingData: "Something went wrong while retrieving the data. Please try again later.",
            errorCodes: {
                "ERR-1": 'Error 1'
            }
        },
        fields: {},
        screens: {
            header: {
                welcomeTitle: 'Fides Blue Pages',
                welcomeSubTitle: '',
                registerOrganization: 'Register organisation',
                about: 'About'
            },
            searchDid: {
                intro: {
                    title: 'Welcome to the Fides Blue Pages',
                    subTitle: 'This is your go-to resource for exploring trusted digital credentials of organisations. \n' +
                        'Search or register your organisation. '
                },
                search: {
                    label: 'Search',
                    placeholder: 'Search organisation by name, KVK or VAT ID'
                },
                noResults: 'No results found'
            },
            addDid: {
                title: 'Register your organisation',
                subTitle: 'Add your organisation to the list so others can easily find you.',
                didLabel: 'DID',
                didInputPlaceholder: 'Enter your organisation\'s DID',
                registerButtonLabel: 'Register organisation',
                didNotFoundTryAgain: 'Try again',
                didNotFoundErrorTitle: 'The DID isn’t valid',
                didNotFoundErrorBody: 'We could not find this DID. Please check the DID and try again.',
                schemaInfo: {
                    short: {
                        title: 'Company profile',
                        body: 'To be able to show information about your company on the Blue Pages, you need to provide an Organization Credential.'
                    },
                    long: {
                        body: 'The Company Credential must use the Organization schema from <a href="https://schema.org/Organization" style="text-decoration: underline">schema.org</a> or use the <a href="/api/webpublic/schema/organization" style="text-decoration: underline">Blue Pages Company profile</a> which is a small subset of the Organization schema.',
                        example: ''
                    }
                }

            },
            addDidPreview: {
                addButtonLabel: 'Add to Blue Pages',
                returnToOverview: 'Return to overview',
                addDidFailedErrorTitle: 'Adding organisation failed',
                addDidFailedErrorBody: 'Something went wrong while adding your organisation. Please try again later.'
            },
            addDidCompleted: {
                title: 'Organisation added',
                subTitle: 'Your organisation is contributing to a reliable and trustworthy world.',
                addButtonLabel: 'Add another organisation',
                returnToOverview: 'Return to overview'
            },
            trustedIssuersList: {
                title: 'Trusted issuers',
                subTitle: 'The following issuers are trusted by the Fides Blue Pages.'
            },
            introduction: {
                title: 'About Blue Pages',
                intro: '<p>FIDES Blue Pages is the result of an experimental project by Dutch Tax Office, Unifiedpost, Dutch Chamber of Commerce, Credenco and FIDESLabs.</p> ' +
                       '<p>It allows you to find verifiable, up to date information about organizations that you want to do business with. The data is based on what trusted ' +
                        '3rd parties (“Digital Identity Anchors” and “Trusted Issuers”) say about the organizations, combined with information that organizations publish' +
                        'about themselves (“Self Descriptions”). It allows you to be confident that you are dealing with trustworthy organizations and provides machine' +
                        'readible information about how to digitally interact with the organization. This includes information about various organizational identifiers,' +
                        'cryptographic key materials (needed for communication) and all kinds of service end points. For instance for sending and receiving eInvoices or' +
                        'all kinds of other documents involved in international trade B2B, B2C or B2G processes.</p>' +
                        '<p>The initiative experiment focuses on the use case of eInvoicing across different jurisdictions. How can you easily find out if your trading partner' +
                        'is supporting eInvoicing, if so which methods, transport mechanisms etc. More info about the <3>eInvoicing use case</3>.</p>' +
                        '<p>FIDES Blue Pages should be considered as an example of a “federated catalog” or “federated registry”. Kind of a “Decentralized Google Business' +
                        'Registry”. It makes use of <strong>Decentralized Identifiers</strong> and <strong>Verifiable Credentials</strong> which allows organizations to maintain their own “public ' +
                        'profile” in new types of tooling which are called “organizational wallets”. The ”federated catalog architecture” in combination with DID’s and ' +
                        'VC\’s may also be used for different type of catalogs, registries or Identity Resolvers. For instance a catalog that resolves products. More ' +
                        'information about the architecture.</p>' +
                        '<p>FIDES Bluepages crawls the DID documents of organizations and displays if the mapped verifible credentials are valid and issued by <strong>Trusted Issuers</strong>. Blue Pages uses this <0>list of trusted issuers per credential type</0> to deisplay if data can be considered trusted.</p>' +
                        '<p>Any organization can be added to FIDES Bluepages. From that moment on the organization can update their profille using  any compliant “organizational wallet” tooling. Please check this page for steps how to register an organization in Blue Pages.</p>' +
                        '<p>Organizational wallets or any kind of other tools can interact with Blue Pages via an <1>API interface.</1></p>' +
                        '<p>FIDES Bluepages is an open source project. Details can be found in a public <2>Github repository</2>. Feedback and contributions are welcome.</p> ' +
                        '<p>For any other information or questions please send a mail to info@fides.community</p>'
            },
            architecture: {
                title: 'Architecture',
                subTitle: 'The following diagram shows the high-level architecture of the solution.',
                description: '<p>The solution consists of three parts, the Blue Pages Frontend, Blue Pages Backend and a number of DID Crawlers.</p> ' +
                    '<p>The Blue Pages Frontend will be used by representatives of legal entities to search for veriable information about other legal entities. ' +
                    'For instance to search for Chamber of Commerce information of other legal entities or to verify the IBAN number of the legal entity. ' +
                    'The solution can be extended to other type of information, such as GS1 identifiers, LEI identifier, addresses etc. This concept ' +
                    'could be considered as a decentralized Google business catalog. </p>' +
                    '<p>The Blue Pages Backend contains the business logic of the Blue Pages. It provides the functionality for storing and retrieving ' +
                    'veriable information. Furthermore an API is implemented, that allows parties to register new DIDs and search for information.</p>' +
                    '<p>The DID Crawlers provide functionality for crawling particular types of DIDs. The Web DID crawlers will be able to crawl ' +
                    'the Linked Verifiable Presentations and other service endpoints from Web DIDs that have been registered in the Blue Pages. ' +
                    'The EBSI DID crawlers have the possibility to retrieve information from the EBSI DID Registry, in which case registration of DIDs ' +
                    'is not necessary. The crawlers retrieve the information from the Linked Verifiable Presentation and will also store the DIDs from ' +
                    'the organizations that issued the credential(s), which can then be crawled again for further information. </p>'
            },
            eInvoice: {
                title: 'Business eInvoicing with Decentralized Identifiers - Process Flow',
                overview: {
                    title: 'Overview',
                    description: 'This document describes a real-world use case for the Blue Pages application, demonstrating how decentralized identifiers (DIDs) and verifiable credentials can streamline and secure the business-to-business eInvoicing process. The scenario follows a transaction between two companies, from initial order placement to invoice acceptance, using decentralized identity verification at each step.'
                },
                scenarioDescription: {
                    title: 'Scenario Description',
                    description: 'In this scenario, a customer ("Pierre") from Fabaleus BV purchases custom-printed products from VistaPrint. The process illustrates how DIDs and the Blue Pages registry facilitate secure, efficient business transactions with automatic identity verification and electronic invoice handling.'
                },
                processFlowDiagram: {
                    title: 'Process Flow Diagram',
                    description: 'The following image shows the process flow:'
                },
                detailedProcessFlow: {
                    title: 'Detailed Process Flow',
                    step1: '<strong>Customer Places Order</strong>: Pierre visits VistaPrint\'s website and orders 20 custom-printed water bottles (doppers) with Fabaleus BV\'s logo.',
                    step2: '<strong>Business Identification</strong>: During the checkout process, Pierre provides Fabaleus BV\'s Chamber of Commerce (CoC) number to identify the business entity that will receive the invoice.',
                    step3: '<strong>DID Resolution via Blue Pages</strong>: VistaPrint uses the Blue Pages registry (FIDES) to search for Fabaleus BV\'s decentralized identifier (DID) using the provided CoC number.<br/>' +
                        '    - Blue Pages searches its registry and selects the correct match using identifiers (trading name, RSIN, CoC number, etc.)</br>' +
                        '    - Blue Pages returns the DID identifier of Fabaleus BV to VistaPrint\'s ERP system',
                    step4: '<strong>Verification Request</strong>: VistaPrint\'s ERP system asks its Organizational Wallet (OWA) to verify Fabaleus BV\'s identity data.',
                    step5: '<strong>DID Document Retrieval</strong>: VistaPrint\'s Organizational Wallet resolves Fabaleus BV\'s DID document through EBSI (European Blockchain Service Infrastructure).<br/>' +
                        '    - The DID document contains cryptographic proof of identity, service endpoints, and credential information',
                    step6: '<strong>Endpoint Resolution</strong>: VistaPrint\'s wallet extracts the service endpoint information from Fabaleus BV\'s DID document.',
                    step7: '<strong>DID Control Verification</strong>: VistaPrint\'s wallet communicates with Fabaleus BV\'s Organizational Wallet (OWB) to verify that it controls the claimed DID.<br/>' +
                        '    - This prevents spoofing and ensures the DID is active and valid',
                    step8: '<strong>Credential Verification</strong>: VistaPrint\'s wallet verifies Fabaleus BV\'s business credentials:<br/>' +
                        '    - LPID (business registry credential)<br/>' +
                        '    - VAT number credential<br/>' +
                        '    - Additional linked verifiable presentations',
                    step9: '<strong>Trust Framework Verification</strong>: The wallet verifies the linked trust credentials against established trust frameworks.',
                    step10: '<strong>Revocation Check</strong>: The wallet checks that none of the credentials have been revoked.',
                    step11: '<strong>Connection Establishment</strong>: VistaPrint\'s wallet creates a secure connection record with Fabaleus BV\'s wallet for future communications.',
                    step12: '<strong>Verification Completion</strong>: The verified business data of Fabaleus BV is returned to VistaPrint\'s ERP system. Fabaleus BV is accepted as a customer',
                    step13: '<strong>Customer Creation</strong>: VistaPrint\'s ERP system creates a customer record using the verified data of Fabaleus BV.',
                    step14: '<strong>Invoice Generation</strong>: VistaPrint\'s ERP system generates an electronic invoice for the order.',
                    step15: '<strong>Document Sealing</strong>: VistaPrint\'s ERP system requests its Organizational Wallet to seal the eInvoice using the company\'s X509 Qualified Electronic Seal (QSeal) certificate.<br/>' +
                        '    - The sealed invoice contains cryptographic proof of its authenticity and integrity<br/>' +
                        '    - The DID information may be included via one of several mechanisms:<br/>' +
                        '        - Option 1: No DID information included (relies on Blue Pages lookup)<br/>' +
                        '        - Option 2: DID document included in the Subject Alternative Name field of the X509 certificate<br/>' +
                        '        - Option 3: DID document included as part of a UBL extension in the invoice',
                    step16: '<strong>Invoice Delivery</strong>: VistaPrint\'s ERP system sends the signed eInvoice to Fabaleus BV\'s ERP system using one of the following methods:<br/>' +
                        '    - Through the Peppol network using the endpoint specified in Fabaleus BV\'s DID document<br/>' +
                        '    - Via direct HTTP POST to Fabaleus BV\'s service endpoint with appropriate authorization token<br/>' +
                        '    - The selected method in this scenario is HTTP POST with access_token_party_A',
                    step17: '<strong>Authorization</strong>: When using HTTP POST:<br/>' +
                        '    - VistaPrint includes an access token (access_token_party_A) in the Authorization header<br/>' +
                        '    - This token proves VistaPrint\'s identity and authorization to submit the invoice',
                    step18: '<strong>Verification Request</strong>: Fabaleus BV\'s ERP system forwards the eInvoice to its Organizational Wallet for verification.',
                    step19: '<strong>Sender Identity Resolution</strong>: Fabaleus BV\'s wallet resolves VistaPrint\'s DID document from the access token issuer information.<br/>' +
                        '    - If no DID information was included in the invoice, it may search Blue Pages to find the match<br/>' +
                        '    - If DID was included in the certificate or UBL extension, it can directly resolve the document<br/>' +
                        '    - If an access_token was send, it\'s possible to use DNS + /did/.well-known to resolve the DID document ',
                    step20: '<strong>Credential Verification</strong>: Fabaleus BV\'s wallet verifies VistaPrint\'s business credentials, including:<br/>' +
                        '    - Business registry credentials<br/>' +
                        '    - VAT number credentials<br/>' +
                        '    - Additional linked verifiable presentations',
                    step21: '<strong>Trust Framework Verification</strong>: The wallet verifies VistaPrint\'s linked trust credentials.',
                    step22: '<strong>Revocation Check</strong>: The wallet checks that none of VistaPrint\'s credentials have been revoked.',
                    step23: '<strong>Connection Establishment</strong>: Fabaleus BV\'s wallet creates a connection record for VistaPrint\'s wallet.',
                    step24: '<strong>Acknowledgment</strong>: Fabaleus BV\'s wallet sends an acknowledgment (ACK) to VistaPrint.',
                    step25: '<strong>Semantic Verification</strong>: Fabaleus BV\'s ERP system verifies the semantic content of the invoice.',
                    step26: '<strong>Signature Verification</strong>: Fabaleus BV\'s wallet verifies the signature and integrity of the eInvoice.',
                    step27: '<strong>Receipt Timestamp</strong>: Fabaleus BV\'s wallet timestamps the received eInvoice and provides this timestamp to the ERP system.',
                    step28: '<strong>Final Acknowledgment</strong>: Fabaleus BV\'s ERP system sends a final receipt acknowledgment to VistaPrint\'s ERP system.',
                    step29: '<strong>Invoice Acceptance</strong>: Fabaleus BV accepts the eInvoice, completing the transaction.',
                },
                benefits: {
                    title: 'Benefits of This Approach',
                    benefit1: '<strong>Automated Identity Verification</strong>: Eliminates manual verification of business credentials.',
                    benefit2: '<strong>Enhanced Security</strong>: Cryptographic proofs ensure the authenticity of all parties and documents.',
                    benefit3: '<strong>Streamlined Process</strong>: Service endpoints in DID documents enable direct system-to-system communication.',
                    benefit4: '<strong>Interoperability</strong>: Works across different ERP systems and electronic invoice formats.',
                    benefit5: '<strong>Regulatory Compliance</strong>: Supports requirements for electronic invoicing and business identification.',
                    benefit6: '<strong>Reduced Fraud</strong>: Cryptographic verification prevents identity spoofing and document tampering.',
                    benefit7: '<strong>Audit Trail</strong>: All steps create verifiable records for future reference or audit purposes.'
                },
                technicalRequirements: {
                    title: 'Technical Requirements',
                    description: 'To implement this scenario, businesses need:',
                    requirement1: '<strong>ERP Integration</strong>: ERP systems capable of resolving DIDs and processing eInvoices',
                    requirement2: '<strong>Organizational Wallet</strong>: A DID-compatible wallet for managing business identities and credentials',
                    requirement3: '<strong>Blue Pages Registration</strong>: Business identity registered in the Blue Pages (FIDES) registry',
                    requirement4: '<strong>DID Document</strong>: Published DID document with appropriate service endpoints',
                    requirement5: '<strong>Verifiable Credentials</strong>: Business credentials from authorized issuers',
                    requirement6: '<strong>X509 Certificate</strong>: Qualified Electronic Seal certificate for document signing',
                },
                conclusion: {
                    title: 'Conclusion',
                    description: 'This use case demonstrates how the Blue Pages application, combined with decentralized identity technologies, can transform business-to-business transactions by providing a secure, efficient framework for company verification and eInvoice processing. By leveraging DIDs and verifiable credentials, businesses can reduce administrative overhead, enhance security, and streamline their procurement and billing processes.'
                },
            },
        }
    } as AppTexts;
};

const flatten: (object: any, prefix?: string) => any = (object, prefix = '') =>
    Object.keys(object).reduce(
        (prev, element) =>
            object[element] &&
            typeof object[element] === 'object' &&
            !Array.isArray(object[element])
                ? {...prev, ...flatten(object[element], `${prefix}${element}.`)}
                : {...prev, ...{[`${prefix}${element}`]: object[element]}},
        {},
    );

export const getTranslations = (language: string): {} => {
    const appTexts = getAppTextsEn();
    return flatten(appTexts, '');
}
