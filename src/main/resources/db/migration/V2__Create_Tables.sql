CREATE TABLE roles
(
    id        SERIAL PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(50) UNIQUE NOT NULL,
    name     VARCHAR(50)        NOT NULL,
    surname  VARCHAR(50)        NOT NULL,
    password VARCHAR(100)       NOT NULL
);

CREATE TABLE organization
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(20)
);

CREATE TABLE company
(
    organization_id INTEGER PRIMARY KEY REFERENCES organization (id),
    name            VARCHAR(255) NOT NULL,
    address         VARCHAR(255)
);

CREATE TABLE personal_org
(
    organization_id INTEGER PRIMARY KEY REFERENCES organization (id),
    owner_id        BIGINT,
    FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE organization_user_roles
(
    id              SERIAL PRIMARY KEY,
    role_id         BIGINT,
    user_id         BIGINT,
    organization_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (organization_id) REFERENCES organization (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE pending_invites
(
    id              SERIAL PRIMARY KEY,
    organization_id BIGINT,
    email           VARCHAR(255) NOT NULL,
    role_id         BIGINT,
    FOREIGN KEY (organization_id) REFERENCES organization (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);
