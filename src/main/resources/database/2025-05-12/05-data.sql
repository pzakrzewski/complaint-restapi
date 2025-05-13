--liquibase formatted sql
--changeset pzakrzewski94:5
set foreign_key_checks=0;

insert into users(id, first_name, last_name)
values(1,'Przemek','Zakrzewski');
insert into users(id, first_name, last_name)
values(2,'Tadeusz','Norek');
insert into users(id, first_name, last_name)
values(3,'Karol','Krawczyk');

insert into products(id, name)
values(1, 'Book');
insert into products(id, name)
values(2, 'Game');
insert into products(id, name)
values(3, 'Toy');