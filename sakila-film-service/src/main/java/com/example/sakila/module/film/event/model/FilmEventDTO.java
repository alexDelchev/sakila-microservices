package com.example.sakila.module.film.event.model;

import com.example.sakila.module.actor.event.model.ActorEventDTO;

import java.util.Date;
import java.util.List;

public class FilmEventDTO {

  private String id;

  private String title;

  private String description;

  private Integer releaseYear;

  private List<String> languages;

  private List<String> originalLanguages;

  private Integer rentalDuration;

  private Float rentalRate;

  private Integer length;

  private Float replacementCost;

  private String rating;

  private Date lastUpdate;

  private List<String> specialFeatures;

  private List<String> categories;

  private List<ActorEventDTO> actors;

  private List<InventoryEventDTO> inventories;

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public List<String> getLanguages() {
    return languages;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public List<String> getOriginalLanguages() {
    return originalLanguages;
  }

  public void setOriginalLanguages(List<String> originalLanguages) {
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

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  public List<ActorEventDTO> getActors() {
    return actors;
  }

  public void setActors(List<ActorEventDTO> actors) {
    this.actors = actors;
  }

  public List<InventoryEventDTO> getInventories() {
    return inventories;
  }

  public void setInventories(List<InventoryEventDTO> inventories) {
    this.inventories = inventories;
  }
}
