--liquibase formatted sql
--changeset pzakrzewski94:3
set foreign_key_checks=0;

create table users (
id bigint not null auto_increment,
first_name varchar(255) not null,
last_name varchar(255) not null,
primary key (id));