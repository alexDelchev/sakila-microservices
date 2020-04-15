CREATE TABLE event (
    eventId uuid NOT NULL,
    aggregateId INT NOT NULL,
    data bytea NOT NULL,
    metaData bytea NOT NULL,
    aggregateVersion integer NOT NULL DEFAULT 1,
    rowCreation timestamp without time zone DEFAULT clock_timestamp() NOT NULL
);

ALTER TABLE public.event OWNER TO "${user}";

ALTER TABLE ONLY event
    ADD CONSTRAINT event_pkey PRIMARY KEY (eventId);

ALTER TABLE ONLY event
    ADD CONSTRAINT event_aggregate_id_fkey FOREIGN KEY (aggregateId) REFERENCES aggregate(aggregateId) ON UPDATE CASCADE ON DELETE RESTRICT;

CREATE UNIQUE INDEX idx_unq_aggregateId_aggregateVersion ON event USING btree (aggregateId, aggregateVersion);

ALTER TABLE ONLY event
    ADD CONSTRAINT event_unq_aggregateId_aggregateVersion UNIQUE USING INDEX idx_unq_aggregateId_aggregateVersion;
