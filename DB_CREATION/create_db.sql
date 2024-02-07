create table airports
(
    id   bigserial
        primary key,
    city varchar(255)
);

alter table airports
    owner to postgres;

create table flights
(
    id                   bigserial
        primary key,
    arrival_date_time    timestamp(6),
    departure_date_time  timestamp(6),
    price                double precision,
    arrival_airport_id   bigint
        constraint fkr90ujcvdphv3co3ry7aiel6l4
            references airports,
    departure_airport_id bigint
        constraint fk27lt4nklvbrwsw7x32dw0d05q
            references airports
);

alter table flights
    owner to postgres;

create table roles
(
    id   bigserial
        primary key,
    name varchar(255)
);

alter table roles
    owner to postgres;

create table users
(
    id       bigserial
        primary key,
    password varchar(255),
    username varchar(255)
);

alter table users
    owner to postgres;

create table user_role
(
    user_id bigint not null
        constraint fkj345gk1bovqvfame88rcx7yyx
            references users,
    role_id bigint not null
        constraint fkt7e7djp752sqn6w22i6ocqy6q
            references roles
);

alter table user_role
    owner to postgres;

