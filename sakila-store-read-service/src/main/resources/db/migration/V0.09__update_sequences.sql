SELECT setval('store_store_id_seq', (SELECT MAX(store_id) FROM store));

SELECT setval('staff_staff_id_seq', (SELECT MAX(staff_id) FROM staff));

SELECT setval('inventory_inventory_id_seq', (SELECT MAX(inventory_id) FROM inventory));
