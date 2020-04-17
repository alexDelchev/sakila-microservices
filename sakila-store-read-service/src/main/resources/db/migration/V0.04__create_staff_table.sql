CREATE TABLE staff (
    staff_id integer NOT NULL,
    first_name character varying(45),
    last_name character varying(45),
    address_id smallint,
    email character varying(50),
    store_id smallint,
    active boolean DEFAULT true,
    username character varying(16),
    password character varying(40),
    last_update timestamp without time zone DEFAULT now() NOT NULL,
    picture bytea
);

ALTER TABLE public.staff OWNER TO "${user}";

ALTER TABLE ONLY staff
    ADD CONSTRAINT staff_pkey PRIMARY KEY (staff_id);

CREATE TRIGGER last_updated
    BEFORE UPDATE ON staff
    FOR EACH ROW
    EXECUTE PROCEDURE last_updated();
