ALTER TABLE public.players ADD is_busy boolean NULL;
ALTER TABLE public.players ADD is_at_party boolean NULL;
ALTER TABLE public.players RENAME COLUMN player_points TO holiday_points;
ALTER TABLE public.players ADD session_points int4 NULL;
