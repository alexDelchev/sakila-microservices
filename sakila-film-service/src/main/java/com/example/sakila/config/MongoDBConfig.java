package com.example.sakila.config;


import com.example.sakila.data.migration.mongodb.MongoDBMigrationRunner;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class MongoDBConfig {

  @Value("${mongodb.host}")
  private String host;

  @Value("${mongodb.port}")
  private Integer port;

  @Value("${mongodb.user}")
  private String user;

  @Value("${mongodb.password}")
  private String password;

  @Value("${mongodb.database}")
  private String databaseName;

  @Bean
  public MongoClient mongoClient() {
    ServerAddress address = new ServerAddress(host, port);
    MongoCredential credential = MongoCredential.createCredential(user, databaseName, password.toCharArray());
    return new MongoClient(address, credential, MongoClientOptions.builder().build());
  }

  @Bean
  public MongoDatabase mongoDatabase(MongoClient mongoClient) {
    CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
    MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(codecRegistry);

    MongoDBMigrationRunner migrationRunner = new MongoDBMigrationRunner(database);
    migrationRunner.runMigrations();

    return database;
  }
}
