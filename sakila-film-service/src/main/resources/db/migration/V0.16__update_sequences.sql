SELECT setval('actor_actor_id_seq', (SELECT MAX(actor_id) FROM actor));

SELECT setval('category_category_id_seq', (SELECT MAX(category_id) FROM category));

SELECT setval('film_film_id_seq', (SELECT MAX(film_id) FROM film));

SELECT setval('language_language_id_seq', (SELECT MAX(language_id) FROM language));
