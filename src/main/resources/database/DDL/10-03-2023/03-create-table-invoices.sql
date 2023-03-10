--liquibase formatted sql
--changeset FDuda:5
CREATE TABLE invoices (
id bigserial PRIMARY KEY,
company_name varchar(200) NOT NULL,
zipCode varchar(6) NOT NULL,
company_address varchar(200) NOT NULL,
company_nip varchar(10) NOT NULL,
date timestamp NOT NULL,
place_date timestamp NOT NULL,
description varchar(1000) NOT NULL,
title varchar(200) NOT NULL,
price_netto numeric(10,2) NOT NULL,
vat numeric(10,2) NOT NULL,
price_brutto numeric(10,2) NOT NULL,
slug varchar(200) NOT NULL,
image varchar(200),
userId bigint NOT NULL
);

