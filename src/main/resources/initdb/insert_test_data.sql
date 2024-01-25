INSERT INTO `performance` (id,name, price, round, type, start_date, is_reserve)
VALUES (unhex(replace('bd84728c-24e3-489e-924b-220568980c62',"-","")),'레베카', 100000, 1, 0, '2024-01-20 19:30:00', 'disable'),
        (unhex(replace('5e3fff8a-3449-4230-86e8-aeb284a1955a',"-","")),'맘마미아',1000,1,0,'2099-02-23 13:30:00','enable');

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO performance_seat_info VALUES
(DEFAULT, (SELECT id FROM performance limit 1 offset 1),1,1,'A',1,'enable',DEFAULT,DEFAULT),
(DEFAULT, (SELECT id FROM performance limit 1 offset 1),1,1,'A',2,'enable',DEFAULT,DEFAULT),
(DEFAULT, (SELECT id FROM performance limit 1 offset 1),1,1,'A',3,'enable',DEFAULT,DEFAULT),
(DEFAULT, (SELECT id FROM performance limit 1 offset 1),1,1,'A',4,'enable',DEFAULT,DEFAULT),
(DEFAULT, (SELECT id FROM performance limit 1 offset 1),1,1,'A',5,'enable',DEFAULT,DEFAULT);
