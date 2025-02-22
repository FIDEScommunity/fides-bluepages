alter table did
    add raw_data_hash binary(32) null;

alter table did
    add raw_data_last_updated_at datetime null;

alter table did
    add last_crawled_at datetime null;
