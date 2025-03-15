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
                        'is supporting eInvoicing, if so which methods, transport mechanisms etc. More info about the eInvocing use case.</p>' +
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
