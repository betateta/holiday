-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public AUTHORIZATION postgres;

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

-- public."role" definition

-- Drop table

-- DROP TABLE public."role";

CREATE TABLE public."role" (
	id int4 NOT NULL,
	rolename varchar NOT NULL,
	CONSTRAINT role_pk PRIMARY KEY (id)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id int4 NOT NULL,
	username varchar NOT NULL,
	"password" varchar NOT NULL,
	"enable" bool NOT NULL,	
	CONSTRAINT users_pk PRIMARY KEY (id),
	CONSTRAINT users_un UNIQUE (username)
);


-- public.sessions definition

-- Drop table

-- DROP TABLE public.sessions;

CREATE TABLE public.sessions (
	id int4 NOT NULL,
	user_id int4 NULL,
	start_time timestamp NULL,
	stop_time timestamp NULL,
	session_points int4 NULL,
	number_of_holidays int4 NULL,
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
	CONSTRAINT sessions_pk PRIMARY KEY (id),
	CONSTRAINT sessions_user_fk FOREIGN KEY (user_id) REFERENCES public.users(id)
);


-- public.calculates definition

-- Drop table

-- DROP TABLE public.calculates;

CREATE TABLE public.calculates (
	id int4 NOT NULL,
	holiday_id int4 NOT NULL,
	session_id int4 NULL,
	capacity int4 NULL,
	start_time timestamp NULL,
	stop_time timestamp NULL,
	uniq_players_number int4 NULL,
	points int4 NULL,	
	CONSTRAINT calculates_pk PRIMARY KEY (id),
	CONSTRAINT calculates_fk FOREIGN KEY (holiday_id) REFERENCES public.holiday(id),
	CONSTRAINT calculates_sessions_fk FOREIGN KEY (session_id) REFERENCES public.sessions(id)
);

-- public.players definition

-- Drop table

-- DROP TABLE public.players;

CREATE TABLE public.players (
	id int4 NOT NULL,
	name varchar NULL,
	player_points int4 NULL,
	is_organizator bool NULL,
	CONSTRAINT players_pk PRIMARY KEY (id)
);


-- public.players_calculates definition

-- Drop table

-- DROP TABLE public.players_calculates;

CREATE TABLE public.players_calculates (
	id int4 NOT NULL,
	player_id int4 NOT NULL,
	calculate_id int4 NOT NULL,
	CONSTRAINT players_calculates_pk PRIMARY KEY (id),
	CONSTRAINT players_calculates_fk FOREIGN KEY (player_id) REFERENCES public.players(id),
	CONSTRAINT players_calculates_fk_1 FOREIGN KEY (calculate_id) REFERENCES public.calculates(id)
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

INSERT INTO public."role" (id,rolename) VALUES
	 (1,'ROLE_USER'),
	 (2,'ROLE_ADMIN');

INSERT INTO public.holiday (id,"name",init_number,duration,min_capacity,max_capacity,points_rate) VALUES
	 (1,'simple',1,3,5,10,0.2),
	 (2,'banquet',1,6,5,20,0.2),
	 (3,'dinner',2,3,5,10,0.3);
	
INSERT INTO public.users (id,username,"password","enable") VALUES
	 (2,'admin','{noop}111',true),
	 (3,'user3','{noop}111',true),	
	 (1,'user','{noop}111',true);

INSERT INTO public.users_roles (id,user_id,role_id) VALUES
	 (1,1,1),
	 (2,1,2),
	 (3,2,2),
	 (4,3,1);
