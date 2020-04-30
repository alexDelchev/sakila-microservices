package com.example.sakila.data.migration.mongodb.model;

import java.util.List;

public class MongoDBCollections {

  private List<String> create;

  private List<MongoDBCollectionUpdate> update;

  private List<MongoDBCollectionData> data;

  public List<String> getCreate() {
    return create;
  }

  public void setCreate(List<String> create) {
    this.create = create;
  }

  public List<MongoDBCollectionUpdate> getUpdate() {
    return update;
  }

  public void setUpdate(List<MongoDBCollectionUpdate> update) {
    this.update = update;
  }

  public List<MongoDBCollectionData> getData() {
    return data;
  }

  public void setData(List<MongoDBCollectionData> data) {
    this.data = data;
  }
}
