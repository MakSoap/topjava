DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
id               INTEGER        AUTO_INCREMENT  PRIMARY KEY,
name             VARCHAR(255)                      NOT NULL,
email            VARCHAR(255)                      NOT NULL,
password         VARCHAR(255)                      NOT NULL,
registered       TIMESTAMP           DEFAULT now() NOT NULL,
enabled          BOOL                DEFAULT TRUE  NOT NULL,
calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
user_id INTEGER NOT NULL,
role    VARCHAR(255),
CONSTRAINT user_roles_idx UNIQUE (user_id, role),
FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

ALTER TABLE users AUTO_INCREMENT=100000;