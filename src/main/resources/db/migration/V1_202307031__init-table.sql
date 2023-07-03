create table if not exists customer (
    id bigserial,
    name varchar(255) not null,
    email varchar(255) not null,
    company varchar(255),
    department varchar(255),
    constraint pk_customer primary key(id)
);

create table if not exists audit_revision (
    id int,
    timestamp bigint not null,
    username varchar(255),
    constraint pk_audit_revision primary key(id)
);

create table if not exists audit_customer (
    id bigint not null,
    revision_id bigint not null,
    revision_type smallint not null,
    name varchar(255),
    name_mod boolean,
    email varchar(255),
    email_mod boolean,
    company varchar(255),
    company_mod boolean,
    constraint pk_customer_audit primary key(id, revision_id),
    constraint fk_audit_cusomter__audit_revision foreign key(revision_id) references audit_revision(id)
);

create sequence audit_revision_seq
    increment by 50;

alter sequence audit_revision_seq owner to user_giash;

