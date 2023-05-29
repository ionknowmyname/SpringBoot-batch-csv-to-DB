DROP TABLE IF EXISTS accounts;

create table accounts (
    id int not null,
    account_name varchar(255),
    allow_reconciliation varchar(255),
    code varchar(255),
    external_id varchar(255),
    type varchar(255),
    primary key (id)
)

