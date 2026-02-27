CREATE SEQUENCE missions_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE missions (
    id BIGINT PRIMARY KEY DEFAULT nextval('missions_seq'),
    user_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    difficulty INTEGER NOT NULL,
    deadline TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);