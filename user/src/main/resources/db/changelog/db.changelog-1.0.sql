--liquibase formatted sql

--changeset artem:1
CREATE TABLE IF NOT EXISTS users
(
    id               SERIAL PRIMARY KEY,
    external_user_id INT       NOT NULL UNIQUE,
    date_created     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_service_profile
(
    id              SERIAL PRIMARY KEY,
    user_id         INT REFERENCES users (id) ON DELETE CASCADE,
    service_id      INT          NOT NULL UNIQUE,
    first_name      VARCHAR(64)  NOT NULL,
    last_name       VARCHAR(64)  NOT NULL,
    phone_number    VARCHAR(16)  NOT NULL UNIQUE,
    post_office_ref VARCHAR(264) NOT NULL,
    date_created    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);