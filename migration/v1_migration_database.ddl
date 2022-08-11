create table authors
(
    id         bigint auto_increment
        primary key,
    deleted    date         null,
    birthday   datetime(6)  null,
    name       varchar(255) not null,
    patronymic varchar(255) null,
    surname    varchar(255) null
);

create table publishers
(
    id      bigint auto_increment
        primary key,
    deleted date         null,
    title   varchar(255) null
);

create table books
(
    id             bigint auto_increment
        primary key,
    deleted        date           null,
    number_of_page int            null,
    price          decimal(19, 2) null,
    release_date   datetime(6)    null,
    title          varchar(255)   null,
    publisher_id   bigint         null,
    constraint UK_pbshnn9d7xsxqert7fjrchpe0
        unique (title),
    constraint FKayy5edfrqnegqj3882nce6qo8
        foreign key (publisher_id) references publishers (id)
);

create table books_authors
(
    written_book_list_id bigint not null,
    authors_id           bigint not null,
    primary key (written_book_list_id, authors_id),
    constraint FK20menrngp9wi9at1dsu5cbb8o
        foreign key (authors_id) references authors (id),
    constraint FK4ubf8cww2y8a1fj78qmkjlo74
        foreign key (written_book_list_id) references books (id)
);

