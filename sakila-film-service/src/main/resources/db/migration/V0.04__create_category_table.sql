CREATE SEQUENCE category_category_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER TABLE public.category_category_id_seq OWNER TO "${user}";

CREATE TABLE category (
    category_id integer DEFAULT nextval('category_category_id_seq'::regclass) NOT NULL,
    name character varying(25) NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

ALTER TABLE public.category OWNER TO "${user}";

ALTER TABLE ONLY category
    ADD CONSTRAINT category_pkey PRIMARY KEY (category_id);

CREATE TRIGGER last_updated
    BEFORE UPDATE ON category
    FOR EACH ROW
    EXECUTE PROCEDURE last_updated();
