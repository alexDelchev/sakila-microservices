package com.example.sakila.data.migration.mongodb.model;

import java.util.ArrayList;
import java.util.List;

public class MongoDBIndex {

  public MongoDBIndex(String type) {
    this.type = type;
    this.fields = new ArrayList<>();
  }

  private String type;

  private List<String> fields;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }
}
