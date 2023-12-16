CREATE TABLE public.users (
	id uuid NOT NULL,
	tg_id varchar(30) NOT NULL,
	chat_id varchar(30) NOT NULL,
	user_name varchar(100) NULL,
	first_name varchar(100) NULL,
	last_name varchar(100) NULL,
	is_bot bool NOT NULL,
	language_code varchar(10) NULL,
	create_date timestamp NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

create table job_history(
	id UUID PRIMARY key,
	user_id UUID NOT null,
	date TIMESTAMP NOT null,
	start_time TIMESTAMP NOT null,
	end_time TIMESTAMP NOT null,
	hours VARCHAR ( 100 ) not null,
	create_date TIMESTAMP NOT null
);