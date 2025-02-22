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
        }
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
            }


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
