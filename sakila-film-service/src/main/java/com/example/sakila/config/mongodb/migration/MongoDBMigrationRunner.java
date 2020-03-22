package com.example.sakila.config.mongodb.migration;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MongoDBMigrationRunner {

  private final Logger log = LoggerFactory.getLogger(MongoDBMigrationRunner.class);

  private final MongoDatabase mongoDatabase;

  private final MongoDBMigrationChecker migrationChecker;

  public MongoDBMigrationRunner(MongoDatabase mongoDatabase) {
    this.mongoDatabase = mongoDatabase;
    this.migrationChecker = new MongoDBMigrationChecker(this.mongoDatabase);
  }

  public void runMigrations() {
    try {
      List<File> files = getScriptFiles();
      files.sort(File::compareTo);

      log.info("Starting MongoDB migration runner");
      for (File f: files) {
        String name = f.getName();
        String content = new String(Files.readAllBytes(f.toPath()));
        if (!migrationChecker.checkMigration(name, content)) continue;
        processMigration(name, content);
        log.info(String.format("Successfully applied %s", name));
        migrationChecker.logMigration(name, content);
      }
      log.info(String.format("Validated %d migrations", files.size()));
    } catch(IOException | URISyntaxException e) {
      log.error("Error while running mongodb scripts: " + e.getMessage(), e);
    }
  }

  private void processMigration(String name, String content) {
    MongoDBMigration migration = MongoDBMigrationParser.parse(content);
    if (migration.getCollections().getCreate() != null) {
      migration.getCollections().getCreate().forEach(mongoDatabase::createCollection);
    }

    if (migration.getCollections().getUpdate() != null) {
      for (MongoDBCollectionUpdate update: migration.getCollections().getUpdate()) {
        update.getIndicesToBeCreated().forEach(index -> {
          Bson indexDefinition = getIndexDefinition(index);
          mongoDatabase.getCollection(update.getCollectionName()).createIndex(indexDefinition);
        });
      }
    }

    if (migration.getCollections().getData() != null) {
      for (MongoDBCollectionData data: migration.getCollections().getData()) {
        mongoDatabase.getCollection(data.getCollectionName()).insertMany(data.getInsertElements());
      }
    }
  }

  private List<File> getScriptFiles() throws IOException, URISyntaxException {
    URI uri = getClass().getResource("/mongodb/scripts").toURI();
    Path path = Paths.get(uri);
    return Files.walk(path).map(Path::toFile).filter(x -> !x.isDirectory()).collect(Collectors.toList());
  }

  private Bson getIndexDefinition(MongoDBIndex mongoDBIndex) {
    Bson result;

    switch (mongoDBIndex.getType()) {
      case "1":
        result = Indexes.ascending(mongoDBIndex.getFields());
        break;
      case "-1":
        result = Indexes.descending(mongoDBIndex.getFields());
        break;
      case "text":
        final List<Bson> indexedFields = new ArrayList<>();
        mongoDBIndex.getFields().forEach(index -> {
          Bson indexBson = Indexes.text(index);
          indexedFields.add(indexBson);
        });
        result = Indexes.compoundIndex(indexedFields);
        break;
      default:
        throw new RuntimeException(String.format("Index type not supported: %s", mongoDBIndex.getType()));
    }

    return result;
  }
}
