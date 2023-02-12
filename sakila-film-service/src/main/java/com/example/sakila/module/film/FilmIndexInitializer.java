package com.example.sakila.module.film;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import com.example.sakila.generated.server.model.FilmSearchDTO;
import com.example.sakila.module.film.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * FilmIndexInitializer creates the initial ES index from
 * Film data. This is necessary since the service starts
 * with some predefined data in the database migration
 * scripts.
 */
@Component
public class FilmIndexInitializer {

  private static final Logger log = LoggerFactory.getLogger(FilmIndexInitializer.class);

  private static final String INDEX_CONFIG = "/elastic_search/film_index.json";

  private final ApplicationContext context;

  private final FilmRepository filmRepository;

  private final ElasticsearchClient client;

  public FilmIndexInitializer(ApplicationContext applicationContext, FilmRepository filmRepository,
                              ElasticsearchClient elasticsearchClient) {
    this.context = applicationContext;
    this.filmRepository = filmRepository;
    this.client = elasticsearchClient;
  }

  @PostConstruct
  public void init() {
    try {
      ensureIndexExists();
    } catch (IOException e) {
      log.error("Failed initial indexing of film data", e);
      SpringApplication.exit(context, () -> -1);
      System.exit(-1);
    }
  }

  private void ensureIndexExists() throws IOException {
    boolean indexExists = client.indices().exists(e -> e.index(FilmSearchService.INDEX_NAME)).value();

    if (indexExists) {
      return;
    }

    log.info("Performing initial indexing of film data");

    try (InputStream in = this.getClass().getResourceAsStream(INDEX_CONFIG)) {
      client.indices().create(i -> i.index(FilmSearchService.INDEX_NAME).withJson(in));
    }

    indexData();
  }

  private void indexData() throws IOException {
    BulkRequest.Builder bulk = new BulkRequest.Builder();

    Iterator<Film> it = filmRepository.findAll().iterator();
    while (it.hasNext()) {
      Film film = it.next();

      FilmSearchDTO filmSearchDTO = FilmUtils.toSearchDto(film);
      bulk.operations(op ->
          op.index(i ->
              i.index(FilmSearchService.INDEX_NAME)
                  .id(filmSearchDTO.getId())
                  .document(filmSearchDTO)
          ));
    }

    BulkResponse response = client.bulk(bulk.build());

    if (response.errors()) {
      log.error("Errors during initial indexing");
    } else {
      log.info("Successfully indexed initial data");
    }
  }
}
