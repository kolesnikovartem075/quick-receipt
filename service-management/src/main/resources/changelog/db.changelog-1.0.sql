--liquibase formatted sql

--changeset artem:1
CREATE TABLE account
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255),
    nickname     VARCHAR(255) UNIQUE,
    status       VARCHAR(50) DEFAULT 'active',
    date_created TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admin
(
    id               SERIAL PRIMARY KEY,
    external_user_id BIGINT UNIQUE,
    bot_service_id   INTEGER REFERENCES account (id),
    password         VARCHAR(255),
    role             VARCHAR(24)
);


CREATE TABLE account_sender
(
    id              SERIAL PRIMARY KEY,
    account_id  INTEGER UNIQUE REFERENCES account (id) ON DELETE CASCADE,
    first_name      VARCHAR(64)  NOT NULL,
    last_name       VARCHAR(64)  NOT NULL,
    phone_number    VARCHAR(16)  NOT NULL,
    post_office_ref VARCHAR(264) NOT NULL
);