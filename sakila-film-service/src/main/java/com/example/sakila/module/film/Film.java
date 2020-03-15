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

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "film")
@TypeDefs({@TypeDef(name = "string-array", typeClass = StringArrayType.class)})
public class Film {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "film_id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "release_year")
  private Integer releaseYear;

  @ManyToOne
  @JoinColumn(name = "language_id")
  private Language language;

  @ManyToOne
  @JoinColumn(name = "original_language_id")
  private Language originalLanguage;

  @Column(name = "rental_duration")
  private Integer rentalDuration;

  @Column(name = "rental_rate")
  private Float rentalRate;

  @Column(name = "length")
  private Integer length;

  @Column(name = "replacement_cost")
  private Float replacementCost;

  @Column(name = "rating")
  private String rating;

  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  @Type(type = "string-array")
  @Column(name = "special_features")
  private String[] specialFeatures;

  @ManyToMany
  @Basic(fetch = FetchType.LAZY)
  @JoinTable(
      name = "film_actor",
      joinColumns = @JoinColumn(name = "film_id"),
      inverseJoinColumns = @JoinColumn(name = "actor_id")
  )
  private List<Actor> actors;

  @OneToOne
  @JoinTable(
      name = "film_category",
      joinColumns = @JoinColumn(name = "film_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private Category category;

  public Film() {}

  public Film(Long id) {
    this.id = id;
  }

  public Film(
      Long id, String title, String description, Integer releaseYear, Language language, Language originalLanguage,
      Integer rentalDuration, Float rentalRate, Integer length, Float replacementCost, String rating, Date lastUpdate,
      String[] specialFeatures, List<Actor> actors, Category category
  ) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.releaseYear = releaseYear;
    this.language = language;
    this.originalLanguage = originalLanguage;
    this.rentalDuration = rentalDuration;
    this.rentalRate = rentalRate;
    this.length = length;
    this.replacementCost = replacementCost;
    this.rating = rating;
    this.lastUpdate = lastUpdate;
    this.specialFeatures = specialFeatures;
    this.actors = actors;
    this.category = category;
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

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public Language getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(Language originalLanguage) {
    this.originalLanguage = originalLanguage;
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

  public List<Actor> getActors() {
    return actors;
  }

  public void setActors(List<Actor> actors) {
    this.actors = actors;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
