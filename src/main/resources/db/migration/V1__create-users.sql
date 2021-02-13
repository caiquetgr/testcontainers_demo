CREATE TABLE users
(
    id        SERIAL,
    bank_name CHARACTER VARYING NOT NULL,
    balance   MONEY             NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

INSERT INTO users
values (default, 'CaiqueBank', 1000.00),
       (default, 'CaiqueBank', 1000.00)
