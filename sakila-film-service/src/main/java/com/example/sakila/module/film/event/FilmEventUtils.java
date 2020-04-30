package com.example.sakila.module.film.event;

import com.example.sakila.module.actor.Actor;
import com.example.sakila.module.actor.event.ActorEventUtils;
import com.example.sakila.module.actor.event.model.ActorEventDTO;
import com.example.sakila.module.film.*;
import com.example.sakila.module.film.event.model.FilmEventDTO;
import com.example.sakila.module.film.event.model.InventoryEventDTO;

import java.util.List;
import java.util.stream.Collectors;

public class FilmEventUtils {

  public static FilmEventDTO toDTO(Film film) {
    FilmEventDTO dto = new FilmEventDTO();

    dto.setId(film.getId().toHexString());
    dto.setTitle(film.getTitle());
    dto.setDescription(film.getDescription());
    dto.setReleaseYear(film.getReleaseYear());
    dto.setRentalDuration(film.getRentalDuration());
    dto.setRentalRate(film.getRentalRate());
    dto.setLength(film.getLength());
    dto.setReplacementCost(film.getReplacementCost());
    dto.setRating(film.getRating());
    dto.setLastUpdate(film.getLastUpdate());
    dto.setSpecialFeatures(film.getSpecialFeatures());

    List<Language> languages = film.getLanguages();
    if (languages != null) {
      List<String> languagesStrings = convertLanguagesToStrings(languages);
      dto.setLanguages(languagesStrings);
    }

    List<Language> originalLanguages = film.getOriginalLanguages();
    if (originalLanguages != null) {
      List<String> originalLanguagesStrings = convertLanguagesToStrings(originalLanguages);
      dto.setOriginalLanguages(originalLanguagesStrings);
    }

    List<Category> categories = film.getCategories();
    if (categories != null) {
      List<String> categoriesStrings = convertCategoriesToStrings(categories);
      dto.setCategories(categoriesStrings);
    }

    List<Actor> actors = film.getActors();
    if (actors != null) {
      List<ActorEventDTO> actorDtos = convertActors(actors);
      dto.setActors(actorDtos);
    }

    List<Inventory> inventories = film.getInventories();
    if (inventories != null) {
      List<InventoryEventDTO> dtos = convertInventories(inventories);
      dto.setInventories(dtos);
    }

    return dto;
  }

  private static List<String> convertLanguagesToStrings(List<Language> languages) {
    return languages.stream().map(Language::toString).collect(Collectors.toList());
  }

  private static List<String> convertCategoriesToStrings(List<Category> categories) {
    return categories.stream().map(Category::toString).collect(Collectors.toList());
  }

  private static List<ActorEventDTO> convertActors(List<Actor> actors) {
    return actors.stream().map(ActorEventUtils::toDTO).collect(Collectors.toList());
  }

  private static List<InventoryEventDTO> convertInventories(List<Inventory> inventories) {
    return inventories.stream().map(InventoryEventUtils::toDTO).collect(Collectors.toList());
  }
}
