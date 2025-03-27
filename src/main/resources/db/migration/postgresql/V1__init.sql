create table users (
	id serial not null primary key,
	name varchar(50) not null unique,
	password varchar,
	email varchar(100));

create table wallet (
	id serial not null primary key,
	name varchar(50),
	wallet_value numeric(10,2));

create table users_wallet (
	id serial not null primary key,
	wallet integer,
	users integer);

create table wallet_itens (
	id serial not null primary key,
	wallet integer,
	date date,
	type varchar(2),
	description varchar(500),
	iten_value numeric(10,2));
	
alter table users_wallet add constraint fk_users_wallet_user
  foreign key(users) references users(id);

alter table users_wallet add constraint fk_users_wallet_wallet
  foreign key(wallet) references wallet(id);

alter table wallet_itens add constraint fk_wallet_itens
  foreign key(wallet) references wallet(id);	