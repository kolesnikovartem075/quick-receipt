--liquibase formatted sql

--changeset artem:1
CREATE TABLE orders
(
    id           BIGSERIAL PRIMARY KEY,
    account_id   BIGINT,
    user_id      BIGINT,
    description  VARCHAR(264),
    status       VARCHAR(16) NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
