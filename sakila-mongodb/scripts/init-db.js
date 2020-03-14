db.auth('sakila-dba', 'sakila-dba-pass')

db = db.getSiblingDB('sakila-film')

db.createUser({
  user: 'sakila-film-service',
  pwd: 'sakila-dba-pass',
  roles: [
    {
      role: 'readWrite',
      db: 'sakila-film',
    },
  ],
});
