CREATE SEQUENCE customer_customer_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER TABLE public.customer_customer_id_seq OWNER TO "${user}";

CREATE TABLE customer (
    customer_id integer DEFAULT nextval('customer_customer_id_seq'::regclass) NOT NULL,
    store_id smallint NOT NULL,
    first_name character varying(45) NOT NULL,
    last_name character varying(45) NOT NULL,
    email character varying(50),
    address_id smallint NOT NULL,
    activebool boolean DEFAULT true NOT NULL,
    create_date date DEFAULT ('now'::text)::date NOT NULL,
    last_update timestamp without time zone DEFAULT now(),
    active integer
);

ALTER TABLE public.customer OWNER TO "${user}";

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_id);

CREATE INDEX idx_fk_address_id ON customer USING btree (address_id);

CREATE INDEX idx_fk_store_id ON customer USING btree (store_id);

CREATE INDEX idx_last_name ON customer USING btree (last_name);

CREATE TRIGGER last_updated
    BEFORE UPDATE ON customer
    FOR EACH ROW
    EXECUTE PROCEDURE last_updated();
