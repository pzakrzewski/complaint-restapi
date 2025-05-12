--liquibase formatted sql
--changeset pzakrzewski94:2
set foreign_key_checks=0;

create table products (
id bigint not null auto_increment,
name varchar(255) not null,
primary key (id));