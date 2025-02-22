alter table did
    add meets_type_requirements bit not null default 0;

create index did_did_meets_type_requirements_index
    on did (did, meets_type_requirements);
