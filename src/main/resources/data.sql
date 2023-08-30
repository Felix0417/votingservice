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



INSERT INTO DISH(name, price, restaurant_id)
VALUES ('Вареный картофель', 70, 1),
       ('Котлета говяжья', 150, 1),
       ('Суп рыбный', 120, 1),
       ('Водка', 200, 1),

       ('Клецки', 150, 2),
       ('Борщ с пампушками', 280, 2),
       ('Котлета по-киевски', 180, 2),
       ('Горилка', 250, 2),

       ('Шницель говяжий', 300, 3),
       ('Фиш энд Чипс', 350, 3),
       ('Бургер от шефа', 420, 3),
       ('Виски', 350, 3),

       ('Хачапури по-аджарски', 150, 4),
       ('Хинкали порция', 200, 4),
       ('Чахохбили', 250, 4),
       ('Вино', 230, 4),


       ('Люля кебаб', 150, 5),
       ('Казан-кабоб', 220, 5),
       ('Лагман Уйгурский', 190, 5),
       ('Алатская самса', 85, 5);

INSERT INTO VOTE (VOTING_DATE_TIME, RESTAURANT_ID, USER_ID)
VALUES (now(), 1, 1),
       (now(), 1, 2),
       (now(), 2, 3),
       ('2020-01-31', 2, 1);
