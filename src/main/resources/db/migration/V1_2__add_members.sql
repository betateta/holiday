ALTER TABLE public.players_calculates RENAME TO members;
ALTER TABLE public.players DROP CONSTRAINT players_calc_fk;
ALTER TABLE public.players DROP COLUMN holiday_points;
ALTER TABLE public.players DROP COLUMN is_organizator;
ALTER TABLE public.players DROP COLUMN sponsored_holiday;
ALTER TABLE public.players DROP COLUMN is_busy;
ALTER TABLE public.players DROP COLUMN is_at_party;
ALTER TABLE public.members ADD input_time timestamp NULL;
ALTER TABLE public.members ADD output_time timestamp NULL;
ALTER TABLE public.members ADD duration int4 NULL;
ALTER TABLE public.members ADD holiday_points int4 NULL;
ALTER TABLE public.members ADD is_organizator boolean NULL;
ALTER TABLE public.members ALTER COLUMN calculate_id DROP NOT NULL;
ALTER TABLE public.members ALTER COLUMN player_id DROP NOT NULL;


