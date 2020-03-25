package com.example.sakila.module.actor.repository;

import com.example.sakila.module.actor.Actor;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActorRepositoryMongoDBImplementation implements ActorRepository {

  private final MongoDatabase mongoDatabase;

  private final MongoCollection<Actor> actors;

  @Autowired
  public ActorRepositoryMongoDBImplementation(MongoDatabase mongoDatabase) {
    this.mongoDatabase = mongoDatabase;
    this.actors = mongoDatabase.getCollection("actor", Actor.class);
  }

  @Override
  public Actor getActorById(ObjectId id) {
    return actors.find(new BasicDBObject("_id", id)).first();
  }

  @Override
  public Actor insertActor(Actor actor) {
    actors.insertOne(actor);
    return actor;
  }

  @Override
  public Actor updateActor(Actor actor) {
    actors.replaceOne(new BasicDBObject("_id", actor.getId()), actor);
    return actor;
  }

  @Override
  public void deleteActor(Actor actor) {
    actors.deleteOne(new BasicDBObject("_id", actor.getId()));
  }

  private <T> List<T> toList(FindIterable<T> iterable) {
    List<T> result = new ArrayList<>();
    MongoCursor<T> cursor = iterable.cursor();

    cursor.forEachRemaining(result::add);

    return result;
  }
}
