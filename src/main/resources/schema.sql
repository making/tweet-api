CREATE TABLE tweets
(
    uuid       VARCHAR(36) PRIMARY KEY,
    text       VARCHAR(255),
    created_at TIMESTAMP
);