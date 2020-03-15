package com.example.sakila.module.film;

import com.example.sakila.module.actor.Actor;
import com.example.sakila.module.category.Category;
import com.example.sakila.module.language.Language;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

public class Film {

  private Long id;

  private String title;

  private String description;

  private Integer releaseYear;

  private Long languageId;

  private Long originalLanguageId;

  private Integer rentalDuration;

  private Float rentalRate;

  private Integer length;

  private Float replacementCost;

  private String rating;

  private Date lastUpdate;

  private String[] specialFeatures;

  private Long categoryId;

  public Film() {}

  public Film(Long id) {
    this.id = id;
  }

  public Film(
      Long id, String title, String description, Integer releaseYear, Long languageId, Long originalLanguageId,
      Integer rentalDuration, Float rentalRate, Integer length, Float replacementCost, String rating, Date lastUpdate,
      String[] specialFeatures, Long categoryId
  ) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.releaseYear = releaseYear;
    this.languageId = languageId;
    this.originalLanguageId = originalLanguageId;
    this.rentalDuration = rentalDuration;
    this.rentalRate = rentalRate;
    this.length = length;
    this.replacementCost = replacementCost;
    this.rating = rating;
    this.lastUpdate = lastUpdate;
    this.specialFeatures = specialFeatures;
    this.categoryId = categoryId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public Long getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Long languageId) {
    this.languageId = languageId;
  }

  public Long getOriginalLanguageId() {
    return originalLanguageId;
  }

  public void setOriginalLanguageId(Long originalLanguageId) {
    this.originalLanguageId = originalLanguageId;
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

  public String[] getSpecialFeatures() {
    return specialFeatures;
  }

  public void setSpecialFeatures(String[] specialFeatures) {
    this.specialFeatures = specialFeatures;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
}
