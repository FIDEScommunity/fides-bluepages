alter table did_service
    add external_key binary(36) null;

update did_service
set did_service.external_key = uuid();

alter table did_service
    modify external_key binary(36) not null default (uuid());
