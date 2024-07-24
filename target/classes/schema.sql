-- auto-generated definition
create table  CONFIG
(
    ID     INT not null
        primary key,
    NAME   VARCHAR(255),
    VALUE  VARCHAR(255),
    DESC   VARCHAR(300),
    PID    INTEGER default 0,
    REMARK TEXT
);

