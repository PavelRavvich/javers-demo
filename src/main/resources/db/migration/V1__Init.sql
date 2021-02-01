CREATE TABLE IF NOT EXISTS audit_comment
(
    id             BIGSERIAL PRIMARY KEY,
    audit_group_id UUID NOT NULL,
    text           TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS company
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS bank_account
(
    id         BIGSERIAL PRIMARY KEY,
    number     BIGINT UNIQUE NOT NULL,
    balance    NUMERIC(10, 2),
    company_id BIGINT REFERENCES company (id)
);

CREATE TABLE IF NOT EXISTS money_transfer
(
    id           BIGSERIAL PRIMARY KEY,
    volume       NUMERIC(10, 2) NOT NULL,
    datetime     TIMESTAMP      NOT NULL DEFAULT now(),
    sender_id    BIGINT         NOT NULL REFERENCES bank_account (id),
    recipient_id BIGINT         NOT NULL REFERENCES bank_account (id)
);