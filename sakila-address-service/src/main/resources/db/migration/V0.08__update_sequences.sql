SELECT setval('address_address_id_seq', (SELECT MAX(address_id) FROM address));

SELECT setval('city_city_id_seq', (SELECT MAX(city_id) FROM city));

SELECT setval('country_country_id_seq', (SELECT MAX(country_id) FROM country));
