package com.example.sakila.module.film.repository;

import com.example.sakila.module.film.Film;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class FilmRepositoryHibernateImpl implements FilmRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Film getFilmById(Long id) {
    return entityManager.find(Film.class, id);
  }

  @Override
  public List<Film> searchFilmsByTitle(String searchExpression) {
    TypedQuery<Film> query = entityManager.createQuery(
        "SELECT f FROM Film f WHERE LOWER(f.title) LIKE LOWER(:expression)",
        Film.class
    );
    query.setParameter("expression", '%' + searchExpression + '%');
    return query.getResultList();
  }

  @Override
  public List<Film> searchFilmsByDescription(String searchExpression) {
    TypedQuery<Film> query = entityManager.createQuery(
        "SELECT f FROM Film f WHERE f.description LIKE :expression",
        Film.class
    );
    query.setParameter("expression", searchExpression);
    return query.getResultList();
  }

  @Override
  public List<Film> getFilmsByCategory(Long categoryId) {
    TypedQuery<Film> query = createQuery("SELECT f FROM Film f JOIN f.category c WHERE c.id = :categoryId");
    query.setParameter("categoryId", categoryId);
    return query.getResultList();
  }

  @Override
  public List<Film> getFilmsByLanguage(Long languageId) {
    TypedQuery<Film> query = createQuery("SELECT f FROM Film f JOIN f.language l WHERE l.id = :languageId");
    query.setParameter("languageId", languageId);
    return query.getResultList();
  }

  @Override
  public List<Film> getFilmsByRating(String rating) {
    TypedQuery<Film> query = createQuery("SELECT f FROM Film f WHERE CAST(rating AS string) = :filmRating");
    query.setParameter("filmRating", rating);
    return query.getResultList();
  }

  private TypedQuery<Film> createQuery(String sql) {
    return entityManager.createQuery(sql, Film.class);
  }
}
