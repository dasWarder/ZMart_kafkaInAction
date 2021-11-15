DELETE FROM client;
ALTER SEQUENCE client_seq RESTART WITH 1;

INSERT INTO client VALUES
    (1, 'a5fe3d3a-938e-4b02-b0dd-c3673eec75b3', 'Alex', 'Petrov', 'alex_p@gmail.com'),
    (2, 'e3dba1bc-d145-4447-91d8-4c4e5461e421', 'Petr', 'Ivanov', 'petr_i@gmail.com'),
    (3, '34bc92fd-06e5-429d-a6eb-8a8257aac93c', 'Olga', 'Tsvetkova', 'olga_t@gmail.com'),
    (4, '7694163f-4d1b-4a9c-9ead-ef92f05d00ac', 'David', 'Smith', 'david_s@gmail.com'),
    (5, 'a23de2aa-409c-4efd-987d-c9702544eeb7', 'Andrey', 'Babichev', 'Al.94_94@mail.ru');

SELECT setval('client_seq', max(id)) FROM client;