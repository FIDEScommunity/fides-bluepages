CREATE TABLE if not exists exclusive_lock
(
    id                        int auto_increment primary key,
    lock_type                 varchar(30)  NOT NULL,
    locked_owner              varchar(100) NOT NULL,
    lock_expiration_date_time DATETIME     NOT NULL
);

CREATE UNIQUE INDEX UC_exclusive_lock_lock_type ON exclusive_lock (lock_type ASC);

