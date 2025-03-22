--liquibase formatted sql

--changeset artem:1
CREATE TABLE account
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255),
    nickname     VARCHAR(255) UNIQUE,
    status       VARCHAR(50) DEFAULT 'active',
    date_created TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

--changeset artem:2
CREATE TABLE admin
(
    id               BIGSERIAL PRIMARY KEY,
    external_user_id BIGINT UNIQUE,
    account_id       BIGINT REFERENCES account (id),
    role             VARCHAR(24)
);

--changeset artem:3
CREATE TABLE account_sender
(
    id              BIGSERIAL PRIMARY KEY,
    account_id      BIGINT UNIQUE REFERENCES account (id) ON DELETE CASCADE,
    first_name      VARCHAR(64)  NOT NULL,
    last_name       VARCHAR(64)  NOT NULL,
    phone_number    VARCHAR(16)  NOT NULL,
    post_office_ref VARCHAR(264) NOT NULL
);