INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);


INSERT INTO RESTAURANT (NAME)
VALUES ('Яръ'), -- 1
       ('Тарас Бульба'), -- 2
       ('Мартин Иден'), -- 3
       ('Мимино'), -- 4
       ('Чайхана'); -- 5

INSERT INTO DISH (name, restaurant_id, of_date, price)
VALUES ('Вареный картофель', 1, CURRENT_DATE, 70),
       ('Котлета говяжья', 1, CURRENT_DATE, 150),
       ('Суп рыбный', 1, CURRENT_DATE, 120),
       ('Водка', 1, CURRENT_DATE, 200),

       ('Клецки', 2, CURRENT_DATE, 150),
       ('Борщ с пампушками', 2, CURRENT_DATE, 280),
       ('Котлета по-киевски', 2, CURRENT_DATE, 180),
       ('Горилка', 2, CURRENT_DATE, 250),

       ('Шницель говяжий', 3, CURRENT_DATE, 300),
       ('Фиш энд Чипс', 3, CURRENT_DATE, 350),
       ('Бургер от шефа', 3, CURRENT_DATE, 420),
       ('Виски', 3, CURRENT_DATE, 350),

       ('Хачапури по-аджарски', 4, CURRENT_DATE, 150),
       ('Хинкали порция', 4, CURRENT_DATE, 200),
       ('Чахохбили', 4, CURRENT_DATE, 250),
       ('Вино', 4, CURRENT_DATE, 230),


       ('Люля кебаб', 5, CURRENT_DATE, 150),
       ('Казан-кабоб', 5, CURRENT_DATE, 220),
       ('Лагман Уйгурский', 5, CURRENT_DATE, 190),
       ('Алатская самса', 5, CURRENT_DATE, 85),

       ('Курица гриль', 1, '2020-01-31', 270),
       ('Шаурма', 1, '2020-01-31', 150),
       ('Сосиска в тесте', 1, '2020-01-31', 40),
       ('Балтика-7', 1, '2020-01-31', 50);

INSERT INTO VOTE (USER_ID, VOTING_DATE, RESTAURANT_ID)
VALUES (1, CURRENT_DATE, 1),
       (2, CURRENT_DATE, 1),
       (1, '2020-01-31', 2);