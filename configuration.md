# Configuration
This guide will describe how to customize the FIDES Bluepages. The backend of the web application is based on Spring Boot, so the configuration can easily be overwritten using a wide variety of different methods. See also [Externalized Configuration](https://docs.spring.io/spring-boot/reference/features/external-config.html).

The default configuration is based on the eInvoicing use case and can be found in

```
fides-bluepages/fides-blue-pages-backend/src/main/resources/application.yaml
```

## EBSI configuration
The FIDES Bluepages is able to crawl DIDs from the EBSI DID Registry. For EBSI the API for each environment (conformance, pilot, etc.) starts with a different base URL. This base URL can be configured using the following property.

```
externalServices:
  ebsi:
    baseUrl: https://api-pilot.ebsi.eu
```

## Wallet configuration
The linked verifiable presentations are verified using a connected organisation wallet. The connection settings can be configured with the following properties.

```
externalServices:
  organizational-wallet:
    apiKey: <configureInSecret>
    apiBaseUrl: https://wallet.acc.credenco.com/api/
```

## Display configuration
The FIDES Bluepages is a generic business catalog that is fully configurable. Even the display information can be configured. 

### Generic display attributes
The page containing the list of found organizations, displays a title, subtitles and a logo. This information is retrieved from the credentials that are stored in the Linked Verifiable Presentations in the DID documents. For each of the display items, the credential type and attribute containing the information for this display item can be configured.

```
display-config:
  genericAttributes:
    title:
      credentialType: KVKRegistration
      attribute: naam
      fallback:
        credentialType: ContactInfo
        attribute: legalName
    subTitle1:
      credentialType: ContactInfo
      attribute: location.address.addressLocality
    subTitle2:
      credentialType: ContactInfo
      attribute: url
    logo:
      credentialType: ContactInfo
      attribute: logo
```
### Services display information 
The DID documents stored in the FIDES Bluepages contain service endpoints. For each of the endpoint types the title, an icon and the label can be configured. Furthermore the display order of the endpoints can be specified.

``` 
  services:
    eInvoice:
      title: E-invoicing
      icon: pi-receipt
      displayOrder: 10
      serviceTypeLabel: Service type
      serviceEndpointLabel: E-invoice address
    OID4VCI:
      title: OpenID4 Verifiable Credential Issuance
      icon: pi-upload
      displayOrder: 11
      serviceTypeLabel: Service type
      serviceEndpointLabel: Credential Issuer address
    OID4VP:
      title: OpenID4 Verifiable Presentation
      icon: pi-download
      displayOrder: 12
      serviceTypeLabel: Service type
      serviceEndpointLabel: Credential Wallet address
    LinkedDomains:
      title: Linked Domains
      icon: pi-id-card
      displayOrder: 13
      serviceTypeLabel: Service type
      serviceEndpointLabel: Domain addresses
```

### Credential configuration
The DID documents stored in the FIDES Bluepages contain Linked Verifiable Presentations. For each of the credential types in such a presentation the issuance url containing all credential metadata, an icon and the attributes to show can be configured. Furthermore the display order of the credentials can be specified. Only the credentials that are configured here will be shown in the Bluepages. When the DID documents contain other credentials, these will be ignored.

``` 
  credentials:
    KVKRegistration:
      issuanceUrl: https://wallet.acc.credenco.com/public/df803dd4-32c6-4a69-afe5-d807c6701ed7/company/.well-known/openid-credential-issuer
      icon: pi-briefcase
      displayOrder: 3
      attributesToShow:
        kvkNummer:
          type: text
        naam:
          type: text
        rechtsvorm:
          type: text
```

### Crawler configuration
The DID crawlers can be configured in a number of different ways. The following properties determine the scheduling of the crawlers:

```
crawler:
  enabled: true
  ebsi-api-crawler-enabled: true
  crawl-frequency-recently-updated-dids: PT1H
  crawl-frequency-not-recently-updated-dids: PT24H
  recently-changed-duration: PT24H
  max-number-of-concurrent-crawls: 10
```
The scheduling can be adapted as follows:
- enabled - Whether the crawler is enabled or disabled.
- ebsi-api-crawler-enabled - Whether the EBSI crawler is enabled or disabled. The crawler reads the EBSI DID Registry. It keeps track of the latest read entry and the next time it will restart from this entry.
- crawl-frequency-recently-updated-dids: The period all recently updated DIDs will be crawled. In this case the recently updated DIDs will be crawled each hour.
- crawl-frequency-not-recently-updated-dids: The period all not recently updated DIDs will be crawled. In this case the not recently updated DIDs will be crawled each 24 hours.
- recently-changed-duration: The period for which a DID will be marked as recently updated. If the DID has been changed within this period, in this case 24 hour, it will be marked as a recently updated DID.
- max-number-of-concurrent-crawls: The maximum number of concurrent crawls that are running. In this example 10 parallel threads will crawl the DID documents of the registered DID. 


## Local development
For local development, the connection to an organization wallet is mocked. The configuration for the mocked calls can be found in:

```
fides-bluepages/fides-blue-pages-backend/src/main/resources/application-local.yaml
```

In this file the properties can be found in the section that starts with 

```
mock-organizational-wallet-config:
```

### Trusted issuer list
The trusted issuer list that is shown in the About pages is retrieved from a connected organisation wallet. During local development this trusted issuer list can be configured with the following properties. For each credential type, a list of DIDs can be configured.
```
trustedIssuerList:
    -
      credentialType: KVKRegistration
      trustedDids:
        -
          did: did:web:mijnkvk.acc.credenco.com:did:df63abc0-2cc9-49e5-b3f8-42a824ceb9a7
    -
      credentialType: Omzetbelasting
      trustedDids:
        -
          did: did:web:wallet.acc.credenco.com:did:bd064799-e498-4020-a427-1c50710d2ac0
```

### Verifiable Presentation Status
The linked verifiable presentations are verified using a connected organisation wallet. During local development this verification can be mocked with the following configuration properties. 
```
  verifiablePresentationStatus:
    overallStatus: VALID
    statusPerCredential:
      ContactInfo:
        verifiablePresentationStatus: VALID
        policyResults:
          -
            policyName: "expired"
            policyDescription: "Verifies that the credentials expiration date (`exp` for JWTs) has not been exceeded."
            valid: true
          -
            policyName: 'not-before'
            policyDescription: "Verifies that the credentials not-before date (for JWT: nbf, if unavailable: iat - 1 min) is correctly exceeded."
            valid: true
          -
            policyName: "revoked_status_list"
            policyDescription: "Verifies Credential Status"
            valid: true
          -
            policyName: "signature"
            policyDescription: "Checks a JWT credential by verifying its cryptographic signature using the key referenced by the DID in `iss`."
            valid: true
      KVKRegistration:
        verifiablePresentationStatus: VALID
        policyResults:
          -
            policyName: "expired"
            policyDescription: "Verifies that the credentials expiration date (`exp` for JWTs) has not been exceeded."
            valid: true
          -
            policyName: "not-before"
            policyDescription: "Verifies that the credentials not-before date (for JWT: `nbf`, if unavailable: `iat` - 1 min) is correctly exceeded."
            valid: true
          -
            policyName: "allowed-issuer"
            policyDescription: "Checks that the issuer of the credential is present in the supplied list."
            valid: true
          -
            policyName: "revoked_status_list"
            policyDescription: "Verifies Credential Status"
            valid: true
          -
            policyName: "signature"
            policyDescription: "Checks a JWT credential by verifying its cryptographic signature using the key referenced by the DID in `iss`."
            valid: true
```
The overallStatus can be either VALID, INVALID, EXPIRED or UNKNOWN. This represents the result of the verification check. During the verification a number of policies will be evaluated. The result of each policy can be configured in the policyResults details. This is a list of policyName, policyDescription and verification result that can be either true of false.
