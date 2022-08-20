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

create table genres
(
    id         bigint auto_increment
        primary key,
    deleted    date         null,
    genre_name varchar(255) null
);

create table genres_authors
(
    genres_id  bigint not null,
    authors_id bigint not null,
    primary key (genres_id, authors_id),
    constraint UK_56f7elick9x3wxqpe5pjy4tv2
        unique (authors_id),
    constraint FK4virqwng7qgcvciwl2a5fxkrf
        foreign key (genres_id) references genres (id),
    constraint FKq6ygeqe4jhoeqfkl4cgun6k5v
        foreign key (authors_id) references authors (id)
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
    constraint FKayy5edfrqnegqj3882nce6qo8
        foreign key (publisher_id) references publishers (id)
);

create table author_books
(
    author_id bigint not null,
    book_id   bigint not null,
    primary key (author_id, book_id),
    constraint FKkudd737sm6cxhjgn948yk599b
        foreign key (author_id) references authors (id),
    constraint FKm8j8158lb50ea4juixpc78v13
        foreign key (book_id) references books (id)
);

create table genres_books
(
    genres_id bigint not null,
    books_id  bigint not null,
    primary key (genres_id, books_id),
    constraint UK_sojl0q1bdx8qnc7n231wqeyln
        unique (books_id),
    constraint FKc9qygelk8lo08hnblc7wbwfp5
        foreign key (books_id) references books (id),
    constraint FKjctbahsqxcinns2tr68x5vgo9
        foreign key (genres_id) references genres (id)
);

create table users
(
    id       bigint auto_increment
        primary key,
    deleted  date         null,
    enabled  bit          null,
    password varchar(255) null,
    rule     varchar(15)  null,
    username varchar(255) null
);

create table authorities
(
    id bigint not null auto_increment primary key,
    username varchar(255) null,
    authority varchar(255) null
);

create table orders
(
    id         bigint auto_increment
        primary key,
    deleted    date         null,
    status     varchar(255) null,
    order_time datetime(6)  null,
    user_id    bigint       null,
    constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id) references users (id)
);

create table orders_book_entity_list
(
    order_id            bigint not null,
    book_entity_list_id bigint not null,
    primary key (order_id, book_entity_list_id),
    constraint UK_oi6ii9025qovey5gtn5sd2rxe
        unique (book_entity_list_id),
    constraint FKq83aaubuw2osedyt3t8ajspc4
        foreign key (order_id) references orders (id),
    constraint FKs03e4b6ot4q900ash64lbx2po
        foreign key (book_entity_list_id) references books (id)
);


insert into users(id,deleted,enabled,password,rule,username)
value (null, null, true,
       '$2a$12$1.q3F7EJCbaM5GKy2fPLeeaX17DtipRRq3n383uK/JN5zNK6GHLdG',
       'USER', 'user');

insert into users(id,deleted,enabled,password,rule,username)
    value (null, null, true,
           '$2a$12$1.q3F7EJCbaM5GKy2fPLeeaX17DtipRRq3n383uK/JN5zNK6GHLdG',
           'ADMIN', 'admin')