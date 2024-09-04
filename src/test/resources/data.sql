INSERT INTO product (id, name, price, stock, description)
VALUES (1, 'Product 1', 100, 100, 'Product 1 description'),
       (2, 'testProduct 2', 200, 200, 'Product 2 description'),
       (3, 'Product 3', 300, 300, 'Product 3 description'),
       (4, 'testProduct 4', 400, 400, 'Product 4 description'),
       (5, 'Product 5', 500, 500, 'Product 5 description');

INSERT INTO user_account (uuid, username, password, email, phone_number, role)
VALUES ('123e4567-e89b-12d3-a456-426655440000', 'admin', '$2a$10$D/Z0XOJZ49jPHxL0C9n5xOGgV9HTw.Uu/qkDdEnzToX0HlZlDJR8K',
        'admin@example.com', '123456789', 'ROLE_ADMIN'),
       ('123e4567-e89b-12d3-a456-426655440001', 'user1', '$2a$10$PA78gdp6yHS9LpGzgl9rROpZzhVsH6H0EMpX5MPihdF7UlY/NiDsO',
        'user1@example.com', '234567890', 'ROLE_USER'),
       ('123e4567-e89b-12d3-a456-426655440002', 'user2 ',
        '$2a$10$sjR6xMSDr3v7AafL4pMB5e7YVPbxjfIZldQL.kj0ouBGn20Vw4sLC', 'user2@example.com', '345678901', 'ROLE_USER');

INSERT INTO cart (id, user_uuid)
VALUES (1, '123e4567-e89b-12d3-a456-426655440000'),
       (2, '123e4567-e89b-12d3-a456-426655440001'),
       (3, '123e4567-e89b-12d3-a456-426655440002');

INSERT INTO cart_item (id, cart_id, product_id, quantity)
VALUES (1, 1, 1, 2),
       (2, 1, 3, 1),
       (3, 2, 2, 5),
       (4, 2, 4, 3),
       (5, 3, 1, 1),
       (6, 3, 5, 2),
       (7, 1, 2, 4),
       (8, 2, 3, 1),
       (9, 3, 4, 2),
       (10, 1, 5, 1);