--liquibase formatted sql
--changeset FDuda:2
insert into tasks (name, deadline, completed, notify ,userId)
values
('AAA', '2020-01-01 00:00:00', false,false, 2),
('aaa', '2020-03-01 00:00:00', false,false, 2),
('abab', '2020-04-01 00:00:00', true,false, 2),
('ABABA', '2020-03-02 00:00:00', false,false, 2),
('111', '2020-06-01 00:00:00', true,false, 2),
('Zakupy', '2020-01-01 00:00:00', false,false, 2);