CREATE TABLE processed_event (
    event_id uuid NOT NULL,
    aggregate_id integer NOT NULL,
    aggregate_version integer NOT NULL,
    processed_date timestamp without time zone DEFAULT now() NOT NULL
);

ALTER TABLE public.processed_event OWNER TO "${user}";

ALTER TABLE ONLY processed_event
    ADD CONSTRAINT processed_event_pkey PRIMARY KEY (event_id);
