CREATE TABLE roles
(
    id        SERIAL PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100)       NOT NULL
);

CREATE TABLE organizations
(
    id SERIAL PRIMARY KEY
);

CREATE TABLE companies
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    FOREIGN KEY (id) REFERENCES organizations (id)
);

CREATE TABLE personal_orgs
(
    id       SERIAL PRIMARY KEY,
    owner_id BIGINT,
    FOREIGN KEY (id) REFERENCES organizations (id),
    FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE organization_user_roles
(
    id              SERIAL PRIMARY KEY,
    role_id         BIGINT,
    user_id         BIGINT,
    organization_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (organization_id) REFERENCES organizations (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);
