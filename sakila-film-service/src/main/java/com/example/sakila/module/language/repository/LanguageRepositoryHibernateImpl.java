package com.example.sakila.module.language.repository;

import com.example.sakila.module.language.Language;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class LanguageRepositoryHibernateImpl implements LanguageRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Language getLanguageById(Long id) {
    return entityManager.find(Language.class, id);
  }

  @Override
  public Language getLanguageByFilmId(Long filmId) {
    TypedQuery<Language> query = createQuery("SELECT l FROM Language l JOIN Film f WHERE f.ID = :filmId");
    query.setParameter("filmId", filmId);
    return query.getSingleResult();
  }

  @Override
  public List<Language> getAllLanguages() {
    return createQuery("SELECT l FROM Language l").getResultList();
  }

  private TypedQuery<Language> createQuery(String query) {
    return entityManager.createQuery(query, Language.class);
  }
}
