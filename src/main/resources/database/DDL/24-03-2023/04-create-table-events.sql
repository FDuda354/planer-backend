--liquibase formatted sql
--changeset FDuda:6
CREATE TABLE events (
id bigserial PRIMARY KEY,
name varchar(200) NOT NULL,
date timestamp NOT NULL,
userId bigint NOT NULL
);

