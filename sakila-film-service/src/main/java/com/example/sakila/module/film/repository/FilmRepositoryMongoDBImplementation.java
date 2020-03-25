package com.example.sakila.module.film.repository;

import com.example.sakila.module.film.Category;
import com.example.sakila.module.film.Film;
import com.example.sakila.module.film.Language;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class FilmRepositoryMongoDBImplementation implements FilmRepository {

  private final MongoDatabase database;

  private final MongoCollection<Film> films;
  
  private final Bson actorLookup = Aggregates.lookup("actor", "actors", "_id", "actors");

  @Autowired
  public FilmRepositoryMongoDBImplementation(MongoDatabase database) {
    this.database = database;
    this.films = database.getCollection("film", Film.class);
  }

  @Override
  public Film getFilmById(ObjectId id) {
    List<Bson> parameters = generateAggregateParameters(new BasicDBObject("_id:", id));
    return films.aggregate(parameters).first();
  }

  @Override
  public List<Film> searchFilmsByTitle(String searchExpression) {
    List<Bson> parameters = generateAggregateParameters(
        new BasicDBObject("title", String.format(".*%s.*", searchExpression))
    );
    
    MongoIterable<Film> result = films.aggregate(parameters);

    return toList(result);
  }

  @Override
  public List<Film> searchFilmsByDescription(String searchExpression) {
    List<Bson> parameters = generateAggregateParameters(
        new BasicDBObject("description", String.format(".*%s.*", searchExpression))
    );

    MongoIterable<Film> result = films.aggregate(parameters);

    return toList(result);
  }

  @Override
  public List<Film> getFilmsByCategory(Category category) {
    List<Bson> parameters = generateAggregateParameters(new BasicDBObject("categories", category.toString()));

    MongoIterable<Film> result = films.aggregate(parameters);

    return toList(result);
  }

  @Override
  public List<Film> getFilmsByLanguage(Language language) {
    List<Bson> parameters = generateAggregateParameters(new BasicDBObject("languages", language.toString()));

    MongoIterable<Film> result = films.aggregate(parameters);

    return toList(result);
  }

  @Override
  public List<Film> getFilmsByRating(String rating) {
    List<Bson> parameters = generateAggregateParameters(new BasicDBObject("rating", rating));

    MongoIterable<Film> result = films.aggregate(parameters);

    return toList(result);
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

  private <T>  List<T> toList(MongoIterable<T> find) {
    List<T> result = new ArrayList<>();
    MongoCursor<T> cursor = find.iterator();

    cursor.forEachRemaining(result::add);

    return result;
  }
  
  private List<Bson> generateAggregateParameters(Bson filter) {
    return Arrays.asList(
        Aggregates.match(filter),
        actorLookup
    );
  }
}
