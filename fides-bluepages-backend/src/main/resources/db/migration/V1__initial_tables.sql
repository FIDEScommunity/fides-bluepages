create table organization
(
    id                    int auto_increment primary key,
    external_key          char(36)     not null,
    name                  varchar(100) not null,
    support_email_address varchar(255) not null,
    api_key               varchar(100) not null
);

create unique index organization_external_key_uindex
    on organization (external_key);


create table did
(
    id            int auto_increment primary key,
    external_key varchar(36) not null default (uuid()),
    did           varchar(512) not null,
    raw_data     text        null,
    registered_at datetime     not null
);

create unique index did_uindex
    on did (did);

create table did_service
(
    id                    int auto_increment primary key,
    did_id                int           not null,
    service_id            varchar(255)  not null,
    service_type          varchar(255)  null,
    service_type_json     varchar(4096) null,
    service_endpoint      varchar(512)  null,
    service_endpoint_json varchar(4096) null,
    raw_data              text          null
);

alter table did_service
    add constraint did_service_did_fk
        foreign key (did_id) references did (id) on delete cascade;

create table credential
(
    id             int auto_increment primary key,
    did_service_id int          not null,
    type           varchar(255) null,
    status         varchar(29)  null,
    last_updated   datetime     null,
    content        text         null
);

alter table credential
    add constraint credential_did_service_fk
        foreign key (did_service_id) references did_service (id) on delete cascade;

create table credential_attribute
(
    id              int auto_increment primary key,
    credential_id   int           not null,
    attribute_key   varchar(255)  not null,
    attribute_value varchar(1024) not null
);

alter table credential_attribute
    add constraint credential_attribute_credential_fk
        foreign key (credential_id) references credential (id) on delete cascade;


create table configured_type
(
    id               int auto_increment primary key,
    credential_type  varchar(255) not null,
    issuance_url     varchar(512) not null,
    issuance_content mediumtext   null
);

create unique index configured_type_uindex
    on configured_type (credential_type);

