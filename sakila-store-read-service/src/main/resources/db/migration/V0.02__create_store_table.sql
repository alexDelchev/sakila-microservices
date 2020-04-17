CREATE TABLE store (
    store_id integer NOT NULL,
    manager_staff_id smallint,
    address_id smallint,
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
