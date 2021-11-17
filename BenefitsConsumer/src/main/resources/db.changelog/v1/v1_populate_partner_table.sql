DELETE FROM partner;
ALTER SEQUENCE partner_seq RESTART WITH 1;

INSERT INTO partner VALUES
    (1, 'H&M', 'Clothes store'),
    (2, 'Burger King', 'The best burgers restaurant in the world'),
    (3, 'Skyeng', 'A school of English level'),
    (4, 'Litres', 'The best audio books in Russian'),
    (5, 'ZMart', 'A network of stores');

SELECT setval('partner_seq', max(id)) FROM partner;