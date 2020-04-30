package com.example.sakila.data.migration.mongodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MongoDBMigrationParser {

  private static final Logger log = LoggerFactory.getLogger(MongoDBMigrationParser.class);

  public static MongoDBMigration parse(String content) {
    MongoDBMigration root;

    try {
      HashMap json = new ObjectMapper().readValue(content, HashMap.class);

      root = new MongoDBMigration();
      MongoDBCollections collections = parseCollections((HashMap)json.get("collections"));
      if (collections != null) root.setCollections(collections);
    } catch(JsonProcessingException | ClassCastException e) {
      log.error(String.format("Failed parsing content: %s", e.getMessage()), e);
      root = null;
    }

    return root;
  }

  private static MongoDBCollections parseCollections(HashMap json) {
    MongoDBCollections result = new MongoDBCollections();
    result.setCreate((List)json.get("create"));

    HashMap updatesJson = (HashMap) json.get("update");
    if (updatesJson != null) {
      List<MongoDBCollectionUpdate> updates = new ArrayList<>();
      updatesJson.forEach((key, value) -> {
        MongoDBCollectionUpdate update = parseCollectionUpdate((String) key, (HashMap) value);
        if (update != null) updates.add(update);
      });

      result.setUpdate(updates);
    }

    HashMap dataJson = (HashMap) json.get("data");
    if (dataJson != null) {
      List<MongoDBCollectionData> data = new ArrayList<>();
      dataJson.forEach((key, value) -> {
        try {
          MongoDBCollectionData collectionData = parseData((String) key, (HashMap) value);
          data.add(collectionData);
        } catch (JsonProcessingException e) {
          log.error(String.format("Failed parsing collection data %s", e.getMessage()), e);
        }
      });

      result.setData(data);
    }

    return result;
  }

  private static MongoDBCollectionUpdate parseCollectionUpdate(String collectionName, HashMap json) {
    MongoDBCollectionUpdate result = new MongoDBCollectionUpdate(collectionName);

    List<MongoDBIndex> indices = new ArrayList<>();
    List<HashMap> indicesJson = (List) ((HashMap) json.get("indices")).get("create");
    if (indicesJson == null) return null;

    indicesJson.forEach(indexJson -> {
      MongoDBIndex index = parseIndex(indexJson);
      if (index != null) indices.add(index);
    });

    result.setIndicesToBeCreated(indices);
    return result;
  }

  private static MongoDBIndex parseIndex(HashMap json) {
    MongoDBIndex result = new MongoDBIndex((String)json.get("type"));
    result.setFields((List)json.get("fields"));
    return result;
  }

  private static MongoDBCollectionData parseData(String collectionName, HashMap json) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    MongoDBCollectionData data = new MongoDBCollectionData(collectionName);

    List<Document> documents = new ArrayList<>();
    List<HashMap> dataList = (List) json.get("insert");

    for (HashMap entry: dataList) {
      String entryJson = mapper.writeValueAsString(entry);
      Document document = Document.parse(entryJson);
      documents.add(document);
    }

    data.setInsertElements(documents);
    return data;
  }
}
