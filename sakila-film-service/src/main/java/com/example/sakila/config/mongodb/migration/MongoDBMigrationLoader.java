package com.example.sakila.config.mongodb.migration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MongoDBMigrationLoader {

  private static final Pattern FILE_PATTERN = Pattern.compile(".*[/\\\\](M[\\d]+\\.?[\\d]*.*)\\.json");

  public List<MongoDBMigrationDescription> getMigrationFiles() throws IOException, URISyntaxException {
    String pathString = "/mongodb/scripts";
    Path path = getPath(pathString);
    return Files.walk(path)
        .filter(p -> !Files.isDirectory(p))
        .map(this::parseMigration)
        .collect(Collectors.toList());
  }

  private Path getPath(String path) throws IOException, URISyntaxException {
    Path result;
    URI uri = getClass().getResource(path).toURI();

    if (uri.getScheme().equals("jar")) {
      FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
      result = fileSystem.getPath(path);
    } else {
      result = Paths.get(uri);
    }

    return result;
  }

  private String convertStreamToString(InputStream stream, String encoding) throws IOException {
    StringBuilder stringBuilder = new StringBuilder(Math.max( 16, stream.available()));
    char[] buffer = new char[4096];

    InputStreamReader reader = new InputStreamReader(stream, encoding);
    int count;
    while ((count = reader.read(buffer)) > 0) stringBuilder.append(buffer, 0, count);

    return stringBuilder.toString();
  }

  private MongoDBMigrationDescription parseMigration(Path path){
    String fileName = extractFileName(path.toString());
    if (fileName == null) return null;
    String content;

    try(InputStream stream = Files.newInputStream(path)) {
      content = convertStreamToString(stream, "UTF-8");
    } catch (IOException e) {
      String message = String.format("Failed reading file %s: %s", fileName, e.getMessage());
      throw new RuntimeException(message, e);
    }

    return new MongoDBMigrationDescription(fileName, content);
  }

  private String extractFileName(String path) {
    Matcher matcher = FILE_PATTERN.matcher(path);
    if (!matcher.find()) return null;

    return matcher.group(1);
  }
}
