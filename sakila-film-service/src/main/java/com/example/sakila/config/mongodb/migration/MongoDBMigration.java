package com.example.sakila.config.mongodb.migration;

public class MongoDBMigration {

  private MongoDBCollections collections;

  public MongoDBCollections getCollections() {
    return collections;
  }

  public void setCollections(MongoDBCollections collections) {
    this.collections = collections;
  }
}
