package com.example.sakila.module.category.repository;

import com.example.sakila.module.category.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CategoryRepositoryHibernateImpl implements CategoryRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Category getCategoryById(Long id) {
    return entityManager.find(Category.class, id);
  }

  @Override
  public Category getCategoryByFilmId(Long filmId) {
    TypedQuery<Category> query = createQuery("SELECT c FROM Film f JOIN f.category c WHERE f.ID = :filmId");
    query.setParameter(":filmId", filmId);
    return query.getSingleResult();
  }

  @Override
  public List<Category> getAllCategories() {
    return createQuery("SELECT c FROM Category c").getResultList();
  }

  @Override
  @Transactional
  public Category insertCategory(Category category) {
    entityManager.persist(category);
    entityManager.flush();
    entityManager.refresh(category);
    return category;
  }

  @Override
  @Transactional
  public Category updateCategory(Category category) {
    entityManager.merge(category);
    entityManager.flush();
    entityManager.refresh(category);
    return category;
  }

  private TypedQuery<Category> createQuery(String query) {
    return entityManager.createQuery(query, Category.class);
  }
}
