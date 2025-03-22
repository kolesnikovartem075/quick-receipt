--liquibase formatted sql

--changeset artem:1
CREATE TABLE IF NOT EXISTS users
(
    id               BIGSERIAL PRIMARY KEY,
    external_user_id BIGINT    NOT NULL UNIQUE,
    date_created     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--changeset artem:2
CREATE TABLE IF NOT EXISTS user_profile
(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT REFERENCES users (id) ON DELETE CASCADE,
    account_id      BIGINT          NOT NULL UNIQUE,
    first_name      VARCHAR(64)  NOT NULL,
    last_name       VARCHAR(64)  NOT NULL,
    phone_number    VARCHAR(16)  NOT NULL UNIQUE,
    post_office_ref VARCHAR(264) NOT NULL,
    date_created    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);