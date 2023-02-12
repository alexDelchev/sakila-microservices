package com.example.sakila.module.film.repository;

import com.example.sakila.module.film.Category;
import com.example.sakila.module.film.Film;
import com.example.sakila.module.film.FilmWriteModel;
import com.example.sakila.module.film.Language;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FilmRepositoryMongoDBImplementation implements FilmRepository {

  private final MongoDatabase database;

  private final MongoCollection<Film> readCollection;

  private final MongoCollection<FilmWriteModel> writeCollection;

  private final Bson actorLookup = Aggregates.lookup("actor", "actors", "_id", "actors");

  @Autowired
  public FilmRepositoryMongoDBImplementation(MongoDatabase database) {
    this.database = database;
    this.readCollection = database.getCollection("film", Film.class);
    this.writeCollection = database.getCollection("film", FilmWriteModel.class);
  }

  @Override
  public Film getFilmById(ObjectId id) {
    List<Bson> parameters = generateAggregateParameters(new BasicDBObject("_id", id));
    return readCollection.aggregate(parameters).first();
  }

  @Override
  public Iterable<Film> findAll() {
    return readCollection.aggregate(generateAggregateParameters(new BasicDBObject()));
  }

  @Override
  public List<Film> searchFilmsByTitle(String searchExpression) {
    Map<String, String> regexParameters = new HashMap<>();
    regexParameters.put("$regex", String.format(".*%s.*", searchExpression));
    regexParameters.put("$options", "i");

    List<Bson> parameters = generateAggregateParameters(new BasicDBObject("title", new BasicDBObject(regexParameters)));

    MongoIterable<Film> result = readCollection.aggregate(parameters);

    return toList(result);
  }

  @Override
  public List<Film> searchFilmsByDescription(String searchExpression) {
    Map<String, String> regexParameters = new HashMap<>();
    regexParameters.put("$regex", String.format(".*%s.*", searchExpression));
    regexParameters.put("$options", "i");

    List<Bson> parameters = generateAggregateParameters(
        new BasicDBObject("description", new BasicDBObject(regexParameters))
    );

    MongoIterable<Film> result = readCollection.aggregate(parameters);

    return toList(result);
  }

  @Override
  public List<Film> getFilmsByCategory(Category category) {
    List<Bson> parameters = generateAggregateParameters(new BasicDBObject("categories", category.toString()));

    MongoIterable<Film> result = readCollection.aggregate(parameters);

    return toList(result);
  }

  @Override
  public List<Film> getFilmsByLanguage(Language language) {
    List<Bson> parameters = generateAggregateParameters(new BasicDBObject("languages", language.toString()));

    MongoIterable<Film> result = readCollection.aggregate(parameters);

    return toList(result);
  }

  @Override
  public List<Film> getFilmsByRating(String rating) {
    List<Bson> parameters = generateAggregateParameters(new BasicDBObject("rating", rating));

    MongoIterable<Film> result = readCollection.aggregate(parameters);

    return toList(result);
  }

  @Override
  public FilmWriteModel insertFilm(FilmWriteModel film) {
    film.setLastUpdate(new Date());
    writeCollection.insertOne(film);
    return film;
  }

  @Override
  public FilmWriteModel updateFilm(FilmWriteModel film) {
    film.setLastUpdate(new Date());
    writeCollection.replaceOne(new BasicDBObject("_id", film.getId()), film);
    return film;
  }

  @Override
  public void deleteFilm(Film film) {
    readCollection.deleteOne(new BasicDBObject("_id", film.getId()));
  }

  private <T> List<T> toList(MongoIterable<T> find) {
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
