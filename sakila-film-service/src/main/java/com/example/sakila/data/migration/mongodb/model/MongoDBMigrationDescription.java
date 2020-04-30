package com.example.sakila.data.migration.mongodb.model;

import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MongoDBMigrationDescription {

  private static final String ENCODING = "UTF-8";

  private static final String HASH_ALGORITHM = "MD5";

  private static final Pattern MIGRATION_NUMBER_PATTERN = Pattern.compile("M([\\d]+\\.?[\\d]*)__");

  private String name;

  private String content;

  private String hash;

  private Float number;

  public MongoDBMigrationDescription() {}

  public MongoDBMigrationDescription(String name, String content) {
    this.name = name;
    this.content = content;
    this.hash = generateHash(content);
    this.number = extractMigrationNumber(name);
  }

  private String generateHash(String content) {
    Charset charset = Charset.forName(ENCODING);
    byte[] bytes = content.getBytes(charset);

    MessageDigest digest = getDigest();
    digest.update(bytes);
    byte[] hashedBytes = digest.digest();

    return Hex.toHexString(hashedBytes);
  }

  private MessageDigest getDigest() {
    try {
      return MessageDigest.getInstance(HASH_ALGORITHM);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Failed to load hash algorithm");
    }
  }

  private Float extractMigrationNumber(String name) {
    Matcher matcher = MIGRATION_NUMBER_PATTERN.matcher(name);
    if (!matcher.find()) return null;

    return Float.parseFloat(matcher.group(1));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public Float getNumber() {
    return number;
  }

  public void setNumber(Float number) {
    this.number = number;
  }
}
