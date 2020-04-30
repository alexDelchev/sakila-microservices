package com.example.sakila.data.migration.mongodb;

import java.util.ArrayList;
import java.util.List;

public class MongoDBCollectionUpdate {

  public MongoDBCollectionUpdate(String collectionName) {
    this.collectionName = collectionName;
    this.indicesToBeCreated = new ArrayList<>();
  }

  private String collectionName;

  private List<MongoDBIndex> indicesToBeCreated;

  public String getCollectionName() {
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  public List<MongoDBIndex> getIndicesToBeCreated() {
    return indicesToBeCreated;
  }

  public void setIndicesToBeCreated(List<MongoDBIndex> indicesToBeCreated) {
    this.indicesToBeCreated = indicesToBeCreated;
  }
}
