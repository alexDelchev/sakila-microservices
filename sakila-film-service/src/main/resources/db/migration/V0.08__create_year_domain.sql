CREATE DOMAIN year AS integer
	CONSTRAINT year_check CHECK (((VALUE >= 1901) AND (VALUE <= 2155)));

ALTER DOMAIN public.year OWNER TO "${user}";
