# eInvoicing Service Endpoints in DID Documents

## Introduction

This document describes a proposed scheme for expressing eInvoicing capabilities within a Decentralized Identifier (DID) document. The scheme enables organizations to declare their accepted electronic invoice formats and available transport methods in a standardized way, facilitating automated discovery and interoperability between trading partners.

## Purpose

The primary purpose of this proposal is to:

1. Standardize how eInvoicing capabilities are expressed in DID documents
2. Enable automated discovery of supported invoice formats and transport methods
3. Facilitate interoperability between different eInvoicing systems
4. Reduce the technical barriers to electronic invoice exchange

## Service Endpoint Structure

Within a DID document, eInvoicing capabilities are expressed in the `services` array. Each eInvoicing service consists of:

- **id**: A unique identifier for the service within the DID document
- **type**: Set to "eInvoice" to identify the service type
- **description**: A human-readable description of the service
- **serviceEndpoints**: An array of available transport methods and document formats

### Transport Methods

Each service endpoint defines:

1. **transport_type**: The transport protocol (e.g., "HTTP", "PEPPOL")
2. **description**: A human-readable description of the transport method
3. **uri** or **participant_id**: Depending on the transport type:
    - HTTP endpoints use a **uri** field with a direct HTTPS URL
    - PEPPOL endpoints use a **participant_id** with the formal PEPPOL identifier
4. **supported_document_types**: An array of document formats accepted through this transport method

### Document Types

Each supported document type specifies:

1. **type**: The document type name (e.g., "Invoice", "CreditNote")
2. **specification**: The standard specification (e.g., "UBL 2.1", "EN 16931")
3. **urn**: The formal URN identifier for the document schema

## Example DID Document

Below is an example of a DID document implementing the proposed eInvoicing service endpoint scheme:

```json
{
  "@context": "https://www.w3.org/ns/did/v1",
  "id": "did:web:vistaprint.acc.credenco.com",
  "verificationMethod": [
    …
  ],
  "services": [
    {
      "id": "did:web:vistaprint.acc.credenco.com#einvoice",
      "type": "eInvoice",
      "description": "Electronic invoicing service supporting multiple transport protocols.",
      "serviceEndpoints": [
        {
          "transport_type": "HTTP",
          "description": "Secure document exchange over HTTPS",
          "uri": "https://wallet.acc.credenco.com/public/169946e8-d766-40f0-bee1-e5b2d1e63db2/inbox",
          "supported_document_types": [
            {
              "type": "Invoice",
              "specification": "UBL 2.1",
              "urn": "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
            },
            {
              "type": "CreditNote",
              "specification": "EN 16931",
              "urn": "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2"
            }
          ]
        },
        {
          "transport_type": "PEPPOL",
          "description": "Document exchange via PEPPOL 4-corner model",
          "participant_id": "iso6523-actorid-upis::9944:nl807319508b01",
          "supported_document_types": [
            {
              "type": "Invoice",
              "specification": "UBL 2.1",
              "urn": "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
            },
            {
              "type": "CreditNote",
              "specification": "EN 16931",
              "urn": "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2"
            }
          ]
        }
      ]
    }
  ]
}
```

## Key Benefits

This proposed scheme offers several advantages:

1. **Flexibility**: Organizations can advertise multiple transport methods and document formats
2. **Discoverability**: Trading partners can automatically discover acceptable invoice formats
3. **Interoperability**: Reduces technical barriers between different eInvoicing systems
4. **Extensibility**: The scheme can be extended to support additional transport methods and document types

## Implementation Considerations

When implementing this scheme, consider:

1. **Security**: Ensure proper authentication and encryption for HTTP endpoints
2. **Validation**: Implement proper validation of received documents against the specified schemas
3. **Updates**: Establish processes for updating DID documents when capabilities change
4. **Versioning**: Consider how to handle document format versioning and backwards compatibility

## Status of This Proposal

This scheme is a proposal and not yet finalized. Feedback is welcome on:

1. The structure of service endpoints
2. Additional fields that might be required
3. Compatibility with existing eInvoicing standards
4. Implementation challenges and solutions

## Next Steps

1. Gather feedback from stakeholders
2. Refine the proposal based on feedback
3. Create implementation examples for common platforms
4. Develop validation tools for conformance testing
5. Consider formal standardization through appropriate industry bodies