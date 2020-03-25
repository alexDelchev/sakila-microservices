package com.example.sakila.module.film;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class FilmWriteModel {

  @BsonId
  private ObjectId id;

  private String title;

  private String description;

  private Integer releaseYear;

  private List<Language> languages;

  private List<Language> originalLanguages;

  private Integer rentalDuration;

  private Float rentalRate;

  private Integer length;

  private Float replacementCost;

  private String rating;

  private Date lastUpdate;

  private List<String> specialFeatures;

  private List<Category> categories;

  private List<ObjectId> actors;

  private List<Inventory> inventories;

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(Integer releaseYear) {
    this.releaseYear = releaseYear;
  }

  public List<Language> getLanguages() {
    return languages;
  }

  public void setLanguages(List<Language> languages) {
    this.languages = languages;
  }

  public List<Language> getOriginalLanguages() {
    return originalLanguages;
  }

  public void setOriginalLanguages(List<Language> originalLanguages) {
    this.originalLanguages = originalLanguages;
  }

  public Integer getRentalDuration() {
    return rentalDuration;
  }

  public void setRentalDuration(Integer rentalDuration) {
    this.rentalDuration = rentalDuration;
  }

  public Float getRentalRate() {
    return rentalRate;
  }

  public void setRentalRate(Float rentalRate) {
    this.rentalRate = rentalRate;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Float getReplacementCost() {
    return replacementCost;
  }

  public void setReplacementCost(Float replacementCost) {
    this.replacementCost = replacementCost;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public List<String> getSpecialFeatures() {
    return specialFeatures;
  }

  public void setSpecialFeatures(List<String> specialFeatures) {
    this.specialFeatures = specialFeatures;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public List<ObjectId> getActors() {
    return actors;
  }

  public void setActors(List<ObjectId> actors) {
    this.actors = actors;
  }

  public List<Inventory> getInventories() {
    return inventories;
  }

  public void setInventories(List<Inventory> inventories) {
    this.inventories = inventories;
  }
}
