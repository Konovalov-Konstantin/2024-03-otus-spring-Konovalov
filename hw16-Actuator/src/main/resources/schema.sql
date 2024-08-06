DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS users;

create table authors
(
    id       bigserial primary key,
    fullName varchar(255),
    primary key (id)
);

create table genres
(
    id   bigserial primary key,
    name varchar(255),
    primary key (id)
);

create table books
(
    id        bigserial primary key,
    title     varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id  bigint references genres (id) on delete cascade,
    primary key (id)
);

create table comments
(
    id      bigserial primary key,
    comment varchar(255),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);