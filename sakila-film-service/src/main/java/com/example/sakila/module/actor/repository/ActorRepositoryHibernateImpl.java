package com.example.sakila.module.actor.repository;

import com.example.sakila.module.actor.Actor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ActorRepositoryHibernateImpl implements ActorRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Actor getActorById(Long id) {
    return entityManager.find(Actor.class, id);
  }

  @Override
  public List<Actor> getActorsByFilm(Long filmId) {
    TypedQuery<Actor> query = createQuery("SELECT a FROM Film f JOIN f.actors a WHERE f.id = :filmId");
    query.setParameter("filmId", filmId);
    return query.getResultList();
  }

  @Override
  @Transactional
  public Actor insertActor(Actor actor) {
    entityManager.persist(actor);
    entityManager.flush();
    entityManager.refresh(actor);
    return actor;
  }

  private TypedQuery<Actor> createQuery(String query) {
    return entityManager.createQuery(query, Actor.class);
  }
}
