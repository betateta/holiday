-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION postgres;

COMMENT ON SCHEMA public IS 'standard public schema';
-- public.holiday definition

-- Drop table

-- DROP TABLE public.holiday;

CREATE TABLE public.holiday (
	id int4 NOT NULL,
	"name" varchar NOT NULL,
	init_number int4 NOT NULL,
	duration int4 NOT NULL,
	min_capacity int4 NOT NULL,
	max_capacity int4 NOT NULL,
	points_rate float4 NOT NULL,
	CONSTRAINT holiday_pk PRIMARY KEY (id)
);


-- public.input_parameters definition

-- Drop table

-- DROP TABLE public.input_parameters;

CREATE TABLE public.input_parameters (
	id int4 NOT NULL,
	session_players int4 NULL,
	session_duration int4 NULL,
	players_addshot_chance int4 NULL,
	players_addshot_min int4 NULL,
	players_addshot_max int4 NULL,
	players_number_addshot int4 NULL,
	holiday_sample_freq int4 NULL,
	holiday_fill_chance int4 NULL,
	holiday_push_chance int4 NULL,
	holiday_simple_chance int4 NULL,
	holiday_banquet_chance int4 NULL,
	holiday_dinner_chance int4 NULL,
	CONSTRAINT input_parameters_pk PRIMARY KEY (id)
);


-- public."role" definition

-- Drop table

-- DROP TABLE public."role";

CREATE TABLE public."role" (
	id int4 NOT NULL,
	rolename varchar NOT NULL,
	CONSTRAINT role_pk PRIMARY KEY (id)
);


-- public."session" definition

-- Drop table

-- DROP TABLE public."session";

CREATE TABLE public."session" (
	id int4 NOT NULL,
	start_time timestamp NULL,
	stop_time timestamp NULL,
	points int4 NULL,
	number_of_holidays int4 NULL,
	CONSTRAINT session_pk PRIMARY KEY (id)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id int4 NOT NULL,
	username varchar NOT NULL,
	"password" varchar NOT NULL,
	"enable" bool NOT NULL,
	points int4 NULL,
	input_parameters_id int4 NULL,
	session_id int4 NULL,
	CONSTRAINT users_pk PRIMARY KEY (id),
	CONSTRAINT users_un UNIQUE (username),
	CONSTRAINT users_fk_param FOREIGN KEY (input_parameters_id) REFERENCES public.input_parameters(id),
	CONSTRAINT users_fk_session FOREIGN KEY (session_id) REFERENCES public."session"(id)
);


-- public.calculate definition

-- Drop table

-- DROP TABLE public.calculate;

CREATE TABLE public.calculate (
	id int4 NOT NULL,
	holiday_id int4 NOT NULL,
	session_id int4 NULL,
	capacity int4 NULL,
	start_time timestamp NULL,
	stop_time timestamp NULL,
	uniq_players_number int4 NULL,
	points int4 NULL,
	sponsor_1_id int4 NULL,
	sponsor_2_id int4 NULL,
	CONSTRAINT calculate_pk PRIMARY KEY (id),
	CONSTRAINT calculate_fk FOREIGN KEY (holiday_id) REFERENCES public.holiday(id),
	CONSTRAINT calculate_fk_sp1 FOREIGN KEY (sponsor_1_id) REFERENCES public."users"(id),
	CONSTRAINT calculate_fk_sp2 FOREIGN KEY (sponsor_2_id) REFERENCES public."users"(id)
);

-- public.users_roles definition

-- Drop table

-- DROP TABLE public.users_roles;

CREATE TABLE public.users_roles (
	id int4 NOT NULL,
	user_id int4 NOT NULL,
	role_id int4 NOT NULL,
	CONSTRAINT users_roles_pk PRIMARY KEY (id),
	CONSTRAINT users_roles_fk FOREIGN KEY (user_id) REFERENCES public.users(id),
	CONSTRAINT users_roles_fk_1 FOREIGN KEY (role_id) REFERENCES public."role"(id)
);
