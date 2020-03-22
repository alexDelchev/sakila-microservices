db.auth('sakila-dba', 'sakila-dba-pass')

db = db.getSiblingDB('sakila-film')

db.createUser({
  user: 'sakila_film_service',
  pwd: 'sakila_dba_pass',
  roles: [
    {
      role: 'readWrite',
      db: 'sakila-film',
    },
  ],
});
