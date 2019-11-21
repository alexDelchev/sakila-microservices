CREATE SEQUENCE store_store_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER TABLE public.store_store_id_seq OWNER TO "${user}";

CREATE TABLE store (
    store_id integer DEFAULT nextval('store_store_id_seq'::regclass) NOT NULL,
    manager_staff_id smallint NOT NULL,
    address_id smallint NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

ALTER TABLE public.store OWNER TO "${user}";

ALTER TABLE ONLY store
    ADD CONSTRAINT store_pkey PRIMARY KEY (store_id);

CREATE UNIQUE INDEX idx_unq_manager_staff_id ON store USING btree (manager_staff_id);

CREATE TRIGGER last_updated
    BEFORE UPDATE ON store
    FOR EACH ROW
    EXECUTE PROCEDURE last_updated();
