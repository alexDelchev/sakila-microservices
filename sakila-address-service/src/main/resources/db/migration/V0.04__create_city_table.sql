CREATE SEQUENCE city_city_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER TABLE public.city_city_id_seq OWNER TO "${user}";

CREATE TABLE city (
    city_id integer DEFAULT nextval('city_city_id_seq'::regclass) NOT NULL,
    city character varying(50) NOT NULL,
    country_id smallint NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

ALTER TABLE public.city OWNER TO "${user}";

ALTER TABLE ONLY city
    ADD CONSTRAINT city_pkey PRIMARY KEY (city_id);

CREATE INDEX idx_fk_country_id ON city USING btree (country_id);

CREATE TRIGGER last_updated
    BEFORE UPDATE ON city
    FOR EACH ROW
    EXECUTE PROCEDURE last_updated();

ALTER TABLE ONLY city
    ADD CONSTRAINT city_country_id_fkey FOREIGN KEY (country_id) REFERENCES country(country_id) ON UPDATE CASCADE ON DELETE RESTRICT;

