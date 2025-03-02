# FIDES Blue Pages

FIDES Blue Pages is the result of an experimental project by Dutch Tax Office,
Unifiedpost, Dutch Chamber of Commerce, Credenco and FIDESLabs.

## Benefits of FIDES Blue Pages

FIDES Blue Pages helps organizations:
* **Find trusted information** about organizations they want to work with
* **Verify business details** through reliable third-party sources
* **Access up-to-date data** that organizations share about themselves
* **Connect digitally** with organizations using their verified service endpoints
* **Streamline eInvoicing** across different countries

It allows any party to find verifiable, up-to-date information about organizations
that they want to do business with. The data is based on what trusted 3rd
parties ("Digital Identity Anchors" and "Trusted Issuers") attest about the
organizations, combined with information that organizations publish about
themselves ("Self Descriptions"). It allows any party to be confident that they are
dealing with trustworthy organizations and provides machine-readable information
about how to digitally interact with the organization. This includes
information about various organizational identifiers, cryptographic key
materials (needed for communication) and all kinds of service end points. For
instance for sending and receiving eInvoices or all kinds of other documents
involved in international trade B2B, B2C or B2G processes.

The initiative experiment focuses on the use case of eInvoicing across
different jurisdictions. How can organizations easily find out if their trading partner
is supporting eInvoicing, if so which methods, transport mechanisms etc. [More info
about the eInvoicing use case](use-case-discovery.md)

FIDES Blue Pages should be considered as an example of a "federated catalog" or
"federated registry". Kind of a "Decentralized Google Business Registry". It
makes use of Decentralized Identifiers and Verifiable Credentials which allows
organizations to maintain their own "public profile" in new types of tooling
which are called "organizational wallets". The "federated catalog architecture"
in combination with DID's and VC's may also be used for different type of
catalogs, registries or Identity Resolvers. For instance a catalog that
resolves products. [More information about the architecture](javascript:void(0)).

FIDES Bluepages crawls the DID documents of organizations and displays if the
mapped verifiable credentials are valid and issued by Trusted Issuers. Blue
Pages uses this list of trusted issuers per credential type to display if data
can be considered trusted.

Any organization can be added to FIDES Bluepages. From that moment on the
organization can update their profile using any compliant "organizational
wallet" tooling. Please [check this page](javascript:void(0)) for steps how to register an
organization in Blue Pages.

Organizational wallets or any kind of other tools can interact with Blue Pages
via an API interface.

FIDES Bluepages is an open source project. Details can be found in a [public
Github repository](javascript:void(0)). Feedback and contributions are welcome.

For any other information or questions please send a mail to info@fides.community

Copyright [2025] [Credenco B.V.]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.