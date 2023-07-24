-- ======= 	CREATE TABLE  ========
CREATE TABLE IF NOT EXISTS table_a (
    id integer,
    current_value bytea,
    PRIMARY KEY (id)
);

-- ======= 	INSTALL PGCRYPTO  ========
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ======= 	INSERT DATA  ========
INSERT INTO table_a (id, current_value) VALUES (1, pgp_sym_encrypt('0', 'password'));
INSERT INTO table_a (id, current_value) VALUES (2, pgp_sym_encrypt('0', 'password'));
INSERT INTO table_a (id, current_value) VALUES (3, pgp_sym_encrypt('0', 'password'));