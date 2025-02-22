alter table credential_attribute
    modify attribute_value varchar(1024) null;

alter table credential_attribute
    add attribute_value_text mediumtext null;

