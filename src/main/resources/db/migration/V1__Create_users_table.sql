CREATE TABLE users(
      id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
      created_at TIMESTAMP,
      email VARCHAR(255) NOT NULL,
      password VARCHAR(255) NOT NULL,
      role VARCHAR(255) NOT NULL,
      updated_at TIMESTAMP,
      username VARCHAR(255) NOT NULL,
      PRIMARY KEY(id)
);