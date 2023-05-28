create table accounts (
    id bigint not null,
    account_name varchar(255),
    allow_reconciliation varchar(255),
    code varchar(255),
    external_id varchar(255),
    type varchar(255),
    primary key (id)
)