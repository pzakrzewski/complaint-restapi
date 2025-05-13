--liquibase formatted sql
--changeset pzakrzewski94:4
set foreign_key_checks=0;

ALTER TABLE complaints
    ADD CONSTRAINT complaints_product_id
    FOREIGN KEY (product_id) REFERENCES products(id);

ALTER TABLE complaints
    ADD CONSTRAINT complaints_user_id
    FOREIGN KEY (user_id) REFERENCES users(id);
