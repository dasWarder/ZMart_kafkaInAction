DELETE FROM bonus;
ALTER SEQUENCE bonus_seq RESTART WITH 1;

INSERT INTO bonus VALUES
    (1, 1, 100, '2% discount'),
    (2, 1, 250, '5% discount'),
    (3, 1, 500, '10% discount'),
    (5, 2, 500, 'Free burger'),
    (6, 2, 1000, 'Free combo'),
    (9, 3, 1000, 'Free 5 lessons'),
    (10, 4, 300, 'Free book');

SELECT setval('bonus_seq', max(id)) FROM bonus;