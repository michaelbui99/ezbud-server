CREATE TABLE IF NOT EXISTS users
(
    id   varchar PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS accounts
(
    id        uuid PRIMARY KEY,
    name      varchar,
    on_budget boolean
);

CREATE TABLE IF NOT EXISTS user_account
(
    user_id    varchar,
    account_id uuid,

    PRIMARY KEY (user_id, account_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);