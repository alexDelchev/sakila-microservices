CREATE SEQUENCE country_country_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER TABLE public.country_country_id_seq OWNER TO "${user}";

CREATE TABLE country (
    country_id integer DEFAULT nextval('country_country_id_seq'::regclass) NOT NULL,
    country character varying(50) NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

ALTER TABLE public.country OWNER TO "${user}";

ALTER TABLE ONLY country
    ADD CONSTRAINT country_pkey PRIMARY KEY (country_id);

CREATE TRIGGER last_updated
    BEFORE UPDATE ON country
    FOR EACH ROW
    EXECUTE PROCEDURE last_updated();
