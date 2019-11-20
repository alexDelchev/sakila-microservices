CREATE TABLE film_category (
    film_id smallint NOT NULL,
    category_id smallint NOT NULL,
    last_update timestamp without time zone DEFAULT now() NOT NULL
);

ALTER TABLE public.film_category OWNER TO "${user}";

ALTER TABLE ONLY film_category
    ADD CONSTRAINT film_category_pkey PRIMARY KEY (film_id, category_id);

CREATE TRIGGER last_updated
    BEFORE UPDATE ON film_category
    FOR EACH ROW
    EXECUTE PROCEDURE last_updated();

ALTER TABLE ONLY film_category
    ADD CONSTRAINT film_category_category_id_fkey FOREIGN KEY (category_id) REFERENCES category(category_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY film_category
    ADD CONSTRAINT film_category_film_id_fkey FOREIGN KEY (film_id) REFERENCES film(film_id) ON UPDATE CASCADE ON DELETE RESTRICT;
