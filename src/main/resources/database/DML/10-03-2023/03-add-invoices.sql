--liquibase formatted sql
--changeset FDuda:3
insert into invoices (company_name, zipCode, company_address, company_nip, date, place_date, description, title, price_netto, vat, price_brutto, slug, image, userId)
 values
 ('aaaa', '00-000', 'aaaa', '1234567890', '2019-01-01 00:00:00', '2019-01-01 00:00:00', 'aaaa', 'aaaa', 100.00, 23.00, 123.00, 'aaaa', null, 2),
 ('bbb', '00-000', 'ul. Firma 2', '1234567890', '2019-01-01 00:00:00', '2019-01-01 00:00:00', 'bbb', 'bbbb', 100.00, 23.00, 123.00, 'bbb', null, 2),
 ('ccc', '00-000', 'ccc', '1234567890', '2019-01-01 00:00:00', '2019-01-01 00:00:00', 'ccc', 'cccc', 100.00, 23.00, 123.00, 'cccc', null, 2);

