--liquibase formatted sql
--changeset FDuda:1
insert into tasks (name, deadline, completed, userId)
values
('Task 1', '2020-01-01 00:00:00', false, 2),
('Task 2', '2020-01-01 00:00:00', false, 2),
('Task 3', '2020-01-01 00:00:00', true, 2),
('Task 4', '2020-01-01 00:00:00', false, 2),
('Task 5', '2020-01-01 00:00:00', true, 2),
('Task 6', '2020-01-01 00:00:00', false, 2);