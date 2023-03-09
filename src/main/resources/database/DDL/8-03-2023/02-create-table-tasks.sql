--liquibase formatted sql
--changeset FDuda:4
CREATE TABLE tasks (
id bigserial PRIMARY KEY,
name varchar(200) NOT NULL,
deadline timestamp NOT NULL,
completed boolean NOT NULL,
notify boolean NOT NULL,
userId bigint NOT NULL
);

