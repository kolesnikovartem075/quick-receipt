--liquibase formatted sql

--changeset artem:1
CREATE TABLE account
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(64) NOT NULL,
    nickname     VARCHAR(64) UNIQUE,
    status       VARCHAR(16) NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset artem:2
CREATE TABLE users
(
    id               BIGSERIAL PRIMARY KEY,
    external_user_id BIGINT,
    account_id       BIGINT REFERENCES account (id) ON DELETE CASCADE,
    role             VARCHAR(16) NOT NULL,
    date_created     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (account_id, external_user_id)
);

--changeset artem:3
CREATE TABLE contact
(
    id              BIGSERIAL PRIMARY KEY,
    account_id      BIGINT REFERENCES account (id) ON DELETE CASCADE,
    first_name      VARCHAR(64)  NOT NULL,
    last_name       VARCHAR(64)  NOT NULL,
    middle_name     VARCHAR(64),
    phone_number    VARCHAR(20)  NOT NULL,
    post_office_ref VARCHAR(264) NOT NULL,
    city_ref        VARCHAR(264) NOT NULL,
    date_created    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (account_id, phone_number)
);

--changeset artem:4
CREATE TABLE user_contact
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    contact_id   BIGINT NOT NULL REFERENCES contact (id) ON DELETE CASCADE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, contact_id)
);

--changeset artem:5
CREATE TABLE account_contact
(
    id           BIGSERIAL PRIMARY KEY,
    contact_id   BIGINT REFERENCES contact (id) ON DELETE CASCADE,
    account_id   BIGINT REFERENCES account (id) ON DELETE CASCADE,
    api_key      VARCHAR(255) UNIQUE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (account_id, contact_id)
);