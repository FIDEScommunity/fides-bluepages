alter table credential
    add subject_did varchar(512) null;

create index credential_subject_did_index
    on credential (subject_did);

