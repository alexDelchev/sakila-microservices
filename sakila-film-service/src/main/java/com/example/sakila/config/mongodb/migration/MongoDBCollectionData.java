package com.example.sakila.config.mongodb.migration;

import org.bson.Document;

import java.util.List;

public class MongoDBCollectionData {

  private String collectionName;

  private List<Document> insertElements;

  public MongoDBCollectionData(String collectionName) {
    this.collectionName = collectionName;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  public List<Document> getInsertElements() {
    return insertElements;
  }

  public void setInsertElements(List<Document> insertElements) {
    this.insertElements = insertElements;
  }
}
