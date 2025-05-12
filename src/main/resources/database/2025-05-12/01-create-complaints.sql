--liquibase formatted sql
--changeset pzakrzewski94:1
set foreign_key_checks=0;

create table complaints (
id bigint not null auto_increment,
content varchar(255) not null,
counter bigint not null,
country varchar(255) not null,
created datetime(6) not null,
product_id bigint not null ,
user_id bigint not null ,
primary key (id),
unique (product_id, user_id));