CREATE TABLE IF NOT EXISTS company
(
    id                      BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS account
(
    id                      BIGSERIAL PRIMARY KEY,
    number                  BIGINT UNIQUE NOT NULL,
    company_id              BIGINT REFERENCES company(id)
);