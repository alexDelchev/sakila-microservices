CREATE TYPE mpaa_rating AS ENUM (
    'G',
    'PG',
    'PG-13',
    'R',
    'NC-17'
);

ALTER TYPE public.mpaa_rating OWNER TO "${user}";
