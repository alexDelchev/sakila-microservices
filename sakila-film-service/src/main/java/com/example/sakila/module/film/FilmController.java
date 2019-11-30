package com.example.sakila.module.film;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class FilmController {

  private final FilmService filmService;

  public FilmController(FilmService filmService ){
    this.filmService = filmService;
  } 

}
