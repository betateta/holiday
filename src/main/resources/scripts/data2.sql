INSERT INTO public."role" (id,rolename) VALUES
	 (1,'ROLE_USER'),
	 (2,'ROLE_ADMIN');

INSERT INTO public.holiday (id,"name",init_number,duration,min_capacity,max_capacity,points_rate) VALUES
	 (1,'simple',1,3,5,10,0.2),
	 (2,'banquet',1,6,5,20,0.2),
	 (3,'dinner',2,3,5,10,0.3);

INSERT INTO public.users (id,username,"password","enable",points) VALUES
	 (1,'user','{noop}111',true,0),
	 (2,'admin','{noop}111',true,0),
	 (3,'user3','{noop}111',true,0),
	 (4,'user4','{noop}111',true,0),
	 (5,'user5','{noop}111',true,0),
	 (6,'user6','{noop}111',true,0),
	 (7,'user7','{noop}111',true,0),
	 (8,'user8','{noop}111',true,0),
	 (9,'user9','{noop}111',true,0),
	 (10,'user10','{noop}111',true,0);

INSERT INTO public.users_roles (id,user_id,role_id) VALUES
	 (1,1,1),
	 (2,1,2),
	 (3,2,2),
	 (4,3,1),
	 (5,4,1),
	 (6,5,1),
	 (7,6,1),
	 (8,7,1),
	 (9,8,1),
	 (10,9,1),
	 (11,10,1);

