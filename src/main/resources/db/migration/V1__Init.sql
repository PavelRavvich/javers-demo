CREATE TABLE IF NOT EXISTS company
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS account
(
    id         BIGSERIAL PRIMARY KEY,
    number     BIGINT UNIQUE  NOT NULL,
    balance    NUMERIC(10, 2),
    company_id BIGINT REFERENCES company (id)
);

CREATE TABLE IF NOT EXISTS transfer
(
    id           BIGSERIAL PRIMARY KEY,
    value        NUMERIC(10, 2) NOT NULL,
    datetime     TIMESTAMP      NOT NULL DEFAULT now(),
    sender_id    BIGINT         NOT NULL REFERENCES account (id),
    recipient_id BIGINT         NOT NULL REFERENCES account (id)
);