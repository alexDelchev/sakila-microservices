CREATE SEQUENCE language_language_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER TABLE public.language_language_id_seq OWNER TO "${user}";

CREATE TABLE language (
    language_id integer DEFAULT nextval('language_language_id_seq'::regclass) NOT NULL,
    name character(20) NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

ALTER TABLE public.language OWNER TO "${user}";

ALTER TABLE ONLY language
    ADD CONSTRAINT language_pkey PRIMARY KEY (language_id);

CREATE TRIGGER last_updated
    BEFORE UPDATE ON language
    FOR EACH ROW
    EXECUTE PROCEDURE last_updated();
