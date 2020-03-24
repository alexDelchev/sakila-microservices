package com.example.sakila.module.film.repository;

import com.example.sakila.module.film.Category;
import com.example.sakila.module.film.Film;
import com.example.sakila.module.film.Language;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FilmRepositoryMongoDBImplementation implements FilmRepository {

  private final MongoDatabase database;

  private final MongoCollection<Film> films;

  @Autowired
  public FilmRepositoryMongoDBImplementation(MongoDatabase database) {
    this.database = database;
    this.films = database.getCollection("film", Film.class);
  }

  @Override
  public Film getFilmById(ObjectId id) {
    return films.find(new BasicDBObject("_id:", id)).first();
  }

  @Override
  public List<Film> searchFilmsByTitle(String searchExpression) {
    BasicDBObject query = new BasicDBObject("title", String.format(".*%s.*", searchExpression));
    FindIterable<Film> find = films.find(query);

    return toList(find);
  }

  @Override
  public List<Film> searchFilmsByDescription(String searchExpression) {
    BasicDBObject query = new BasicDBObject("description", String.format(".*%s.*", searchExpression));
    FindIterable<Film> find = films.find(query);

    return toList(find);
  }

  @Override
  public List<Film> getFilmsByCategory(Category category) {
    BasicDBObject query = new BasicDBObject("categories", category.toString());
    FindIterable<Film> find = films.find(query);

    return toList(find);
  }

  @Override
  public List<Film> getFilmsByLanguage(Language language) {
    BasicDBObject query = new BasicDBObject("languages", language.toString());
    FindIterable<Film> find = films.find(query);

    return toList(find);
  }

  @Override
  public List<Film> getFilmsByRating(String rating) {
    BasicDBObject query = new BasicDBObject("rating", rating);
    FindIterable<Film> find = films.find(query);

    return toList(find);
  }

  @Override
  public Film insertFilm(Film film) {
    film.setLastUpdate(new Date());
    films.insertOne(film);
    return film;
  }

  @Override
  public Film updateFilm(Film film) {
    film.setLastUpdate(new Date());
    films.replaceOne(new BasicDBObject("_id", film.getId()), film);
    return film;
  }

  @Override
  public void deleteFilm(Film film) {
    films.deleteOne(new BasicDBObject("_id", film.getId()));
  }

  private <T>  List<T> toList(FindIterable<T> find) {
    List<T> result = new ArrayList<>();
    MongoCursor<T> cursor = find.iterator();

    cursor.forEachRemaining(result::add);

    return result;
  }
}
