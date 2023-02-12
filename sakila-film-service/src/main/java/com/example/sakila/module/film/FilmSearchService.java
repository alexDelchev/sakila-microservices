package com.example.sakila.module.film;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.generated.server.model.ApiFilmCategory;
import com.example.sakila.generated.server.model.FilmRating;
import com.example.sakila.generated.server.model.FilmSearchDTO;
import com.example.sakila.module.film.event.model.FilmCreatedEvent;
import com.example.sakila.module.film.event.model.FilmDeletedEvent;
import com.example.sakila.module.film.event.model.FilmUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmSearchService {

  private static final Logger log = LoggerFactory.getLogger(FilmService.class);

  public static final String INDEX_NAME = "films";

  private final EventBus eventBus;

  private final ElasticsearchClient client;

  public FilmSearchService(@Qualifier("FilmEventBus") EventBus eventBus, ElasticsearchClient elasticsearchClient) {
    this.eventBus = eventBus;
    this.client = elasticsearchClient;
  }

  @PostConstruct
  public void init() {
    this.eventBus.register(this);
  }

  public List<FilmSearchDTO> searchFilms(String searchExperssion, ApiFilmCategory category,
                                         FilmRating filmRating) throws IOException {
    BoolQuery.Builder builder = new BoolQuery.Builder();

    if (StringUtils.hasText(searchExperssion)) {
      builder.must(q -> q.match(m -> m.field("title").query(searchExperssion)));
    }

    if (category != null) {
      builder.must(q -> q.match(m -> m.field("categories").query(category.toString())));
    }

    if (filmRating != null) {
      builder.must(q -> q.match(m -> m.field("rating").query(filmRating.toString())));
    }

    if (!StringUtils.hasText(searchExperssion) && category == null && filmRating == null) {
      builder.must(q -> q.matchAll(m -> m));
    }

    BoolQuery boolQuery = builder.build();

    return client.search((SearchRequest.of(s ->
        s.index(INDEX_NAME)
            .query(q -> q.bool(boolQuery)))), FilmSearchDTO.class)
        .hits().hits()
        .stream().map(Hit::source)
        .collect(Collectors.toList());
  }
  public List<FilmSearchDTO> searchFilmsByTitle(String title) throws IOException {
    return client.search(SearchRequest.of(s ->
            s.index(INDEX_NAME)
                .query(q ->
                    q.match(m -> m.field("title").query(title)))
        ), FilmSearchDTO.class)
        .hits().hits()
        .stream().map(Hit::source)
        .collect(Collectors.toList());
  }

  public List<FilmSearchDTO> searchFilmsByDescription(String description) throws IOException {
    return client.search(SearchRequest.of(s ->
            s.index(INDEX_NAME)
                .query(q ->
                    q.match(m -> m.field("description").query(description)))
        ), FilmSearchDTO.class)
        .hits().hits()
        .stream().map(Hit::source)
        .collect(Collectors.toList());
  }

  @Handler
  public void onFilmCreated(FilmCreatedEvent event) {
    FilmSearchDTO filmSearchDTO = FilmUtils.toSearchDto(event.getDto());

    indexFilm(filmSearchDTO);
  }

  @Handler
  public void onFilmUpdated(FilmUpdatedEvent event) {
    FilmSearchDTO filmSearchDTO = FilmUtils.toSearchDto(event.getDto());

    indexFilm(filmSearchDTO);
  }

  @Handler
  public void onFilmDeleted(FilmDeletedEvent event) {
    deleteFilmFromIndex(event.getId());
  }

  private void indexFilm(FilmSearchDTO filmSearchDTO) {
    try {
      client.index(i -> i.index(INDEX_NAME)
          .id(filmSearchDTO.getId())
          .document(filmSearchDTO));
    } catch (IOException e) {
      log.error("Failed indexing film {}", filmSearchDTO.getId(), e);
    }
  }

  private void deleteFilmFromIndex(String hexId) {
    try {
      client.delete(d -> d.index(INDEX_NAME).id(hexId));
    } catch (IOException e) {
      log.error("Failed deleting film {}", hexId, e);
    }
  }
}
