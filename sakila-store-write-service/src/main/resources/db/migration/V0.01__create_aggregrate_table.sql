CREATE SEQUENCE aggregate_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER TABLE public.aggregate_id_seq OWNER TO "${user}";

CREATE TABLE aggregate (
    aggregateId integer DEFAULT nextval('aggregate_id_seq'::regclass) NOT NULL,
    type varchar NOT NULL,
    version integer DEFAULT 1 NOT NULL,
    lastUpdate timestamp without time zone NOT NULL
);

ALTER TABLE public.aggregate OWNER TO "${user}";

ALTER TABLE ONLY aggregate
    ADD CONSTRAINT aggregate_pkey PRIMARY KEY (aggregateId);
