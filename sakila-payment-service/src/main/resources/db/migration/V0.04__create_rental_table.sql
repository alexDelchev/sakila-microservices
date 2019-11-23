CREATE SEQUENCE rental_rental_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER TABLE public.rental_rental_id_seq OWNER TO "${user}";

CREATE TABLE rental (
    rental_id integer DEFAULT nextval('rental_rental_id_seq'::regclass) NOT NULL,
    rental_date timestamp without time zone NOT NULL,
    inventory_id integer NOT NULL,
    customer_id smallint NOT NULL,
    return_date timestamp without time zone,
    staff_id smallint NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

ALTER TABLE public.rental OWNER TO "${user}";

ALTER TABLE ONLY rental
    ADD CONSTRAINT rental_pkey PRIMARY KEY (rental_id);

CREATE INDEX idx_fk_inventory_id ON rental USING btree (inventory_id);

CREATE UNIQUE INDEX idx_unq_rental_rental_date_inventory_id_customer_id ON rental USING btree (rental_date, inventory_id, customer_id);

CREATE TRIGGER last_updated
    BEFORE UPDATE ON rental
    FOR EACH ROW
    EXECUTE PROCEDURE last_updated();

ALTER TABLE ONLY rental
    ADD CONSTRAINT rental_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE ON DELETE RESTRICT;
