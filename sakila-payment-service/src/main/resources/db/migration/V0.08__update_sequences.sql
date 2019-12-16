SELECT setval('customer_customer_id_seq', (SELECT MAX(customer_id) FROM customer));

SELECT setval('payment_payment_id_seq', (SELECT MAX(payment_id) FROM payment));

SELECT setval('rental_rental_id_seq', (SELECT MAX(rental_id) FROM rental));
