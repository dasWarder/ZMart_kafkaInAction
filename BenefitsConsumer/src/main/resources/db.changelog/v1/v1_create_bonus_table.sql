DROP TABLE IF EXISTS bonus;
DROP SEQUENCE IF EXISTS bonus_seq;

CREATE SEQUENCE bonus_seq START WITH 1;

CREATE TABLE bonus(
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('bonus_seq'),
    partner_id BIGINT NOT NULL,
    reward_points BIGINT NOT NULL,
    description VARCHAR NOT NULL,
    FOREIGN KEY (partner_id) REFERENCES partner(id)
);
