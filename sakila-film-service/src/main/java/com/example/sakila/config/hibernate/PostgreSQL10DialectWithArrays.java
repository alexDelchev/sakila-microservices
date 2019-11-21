package com.example.sakila.config.hibernate;

import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Types;

public class PostgreSQL10DialectWithArrays extends PostgreSQL10Dialect {

  public PostgreSQL10DialectWithArrays() {
    super();
    this.registerColumnType(Types.ARRAY, "array");
  }
}
