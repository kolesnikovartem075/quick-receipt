--liquibase formatted sql

--changeset artem:1
CREATE TABLE orders
(
    id          SERIAL PRIMARY KEY,
    service_id  INT,
    user_id     INT,
    description VARCHAR(264),
    status      VARCHAR(16) NOT NULL,
    date_created  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
