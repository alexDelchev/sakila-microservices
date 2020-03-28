package com.example.sakila.config.mongodb.migration;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDBMigrationChecker {

  private static final String MIGRATION_COLLECTION_NAME = "migration_history";

  private final Logger log = LoggerFactory.getLogger(MongoDBMigrationChecker.class);

  private final MongoDatabase mongoDatabase;

  public MongoDBMigrationChecker(MongoDatabase mongoDatabase) {
    this.mongoDatabase = mongoDatabase;
    init();
  }

  boolean checkMigration(String name, String content) {
    MongoDBMigrationDescription description = new MongoDBMigrationDescription(name, content);

    return checkMigration(description);
  }

  boolean checkMigration(MongoDBMigrationDescription migration) {
    return !migrationAlreadyProcessed(migration);
  }

  void logMigration(String name, String content) {
    MongoDBMigrationDescription description = new MongoDBMigrationDescription(name, content);

    logMigration(description);
  }

  void logMigration(MongoDBMigrationDescription migration) {
    mongoDatabase.getCollection(MIGRATION_COLLECTION_NAME, MongoDBMigrationDescription.class).insertOne(migration);
  }

  private boolean migrationAlreadyProcessed(MongoDBMigrationDescription description) {
    MongoCollection<MongoDBMigrationDescription> collection = mongoDatabase.getCollection(MIGRATION_COLLECTION_NAME, MongoDBMigrationDescription.class);

    String json = String.format("{\"name\": \"%s\"}", description.getName());
    MongoDBMigrationDescription persisted = collection.find(Document.parse(json)).first();
    if (persisted == null) return false;

    if (!description.getHash().equals(persisted.getHash())) {
      throw new RuntimeException(
          String.format("Migration hash mismatch. Expected: [%s], Actual: [%s]", persisted.getHash(), description.getHash())
      );
    }

    return true;
  }

  private void init() {
    if (!migrationCollectionExists()) {
      log.info(String.format("Creating collection %s", MIGRATION_COLLECTION_NAME));
      mongoDatabase.createCollection(MIGRATION_COLLECTION_NAME);
    } else {
      log.info(String.format("Using existing collection %s", MIGRATION_COLLECTION_NAME));
    }
  }

  private boolean migrationCollectionExists() {
    boolean exists = false;

    for (String name: mongoDatabase.listCollectionNames()) {
      if (name.equals(MIGRATION_COLLECTION_NAME)) {
        exists = true;
        break;
      }
    }

    return exists;
  }
}
