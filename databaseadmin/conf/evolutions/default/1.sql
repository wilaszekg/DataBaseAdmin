# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table database_server (
  id                        bigint not null,
  database_type_id          integer,
  ip                        varchar(255),
  port                      integer,
  name                      varchar(255),
  login                     varchar(255),
  password                  varchar(255),
  constraint pk_database_server primary key (id))
;

create table database_type (
  id                        integer not null,
  vendor                    varchar(255),
  name                      varchar(255),
  version                   varchar(255),
  constraint pk_database_type primary key (id))
;

create table user (
  login                     varchar(255) not null,
  password                  varchar(255),
  role                      integer,
  authentication_mode       integer,
  first_name                varchar(255),
  second_name               varchar(255),
  email                     varchar(255),
  constraint ck_user_role check (role in (0,1)),
  constraint ck_user_authentication_mode check (authentication_mode in (0,1)),
  constraint pk_user primary key (login))
;

create sequence database_server_seq;

create sequence database_type_seq;

create sequence user_seq;

alter table database_server add constraint fk_database_server_databaseTyp_1 foreign key (database_type_id) references database_type (id) on delete restrict on update restrict;
create index ix_database_server_databaseTyp_1 on database_server (database_type_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists database_server;

drop table if exists database_type;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists database_server_seq;

drop sequence if exists database_type_seq;

drop sequence if exists user_seq;

