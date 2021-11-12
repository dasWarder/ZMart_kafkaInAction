DROP TABLE IF EXISTS client;
DROP SEQUENCE IF EXISTS client_seq;

CREATE SEQUENCE client_seq START WITH 1;

CREATE TABLE client(
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('client_seq'),
    client_id VARCHAR NOT NULL,
    first_name VARCHAR(256) NOT NULL,
    last_name VARCHAR(256) NOT NULL,
    email VARCHAR(256) NOT NULL
);