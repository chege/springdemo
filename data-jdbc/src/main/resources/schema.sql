create table user(
    id INT IDENTITY PRIMARY KEY,
    username VARCHAR(255) not null,
    email VARCHAR(255) not null
);