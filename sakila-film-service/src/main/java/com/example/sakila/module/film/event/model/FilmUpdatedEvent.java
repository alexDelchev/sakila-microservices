package com.example.sakila.module.film.event.model;

public class FilmUpdatedEvent {

  private FilmEventDTO dto;

  public FilmEventDTO getDto() {
    return dto;
  }

  public void setDto(FilmEventDTO dto) {
    this.dto = dto;
  }
}
