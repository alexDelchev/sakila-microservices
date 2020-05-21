db.auth('sakila_dba', 'sakila_dba_pass')

db = db.getSiblingDB('sakila_film')

db.createUser({
  user: 'sakila_film_service',
  pwd: 'sakila_dba_pass',
  roles: [
    {
      role: 'readWrite',
      db: 'sakila_film',
    },
  ],
});
