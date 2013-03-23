# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  login                     varchar(255) not null,
  password                  varchar(255),
  role                      integer,
  constraint ck_user_role check (role in (0,1)),
  constraint pk_user primary key (login))
;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;

