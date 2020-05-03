package com.example.sakila.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class JPAConfig {

  @Bean
  LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean()
    entityManagerFactoryBean.dataSource = dataSource
    entityManagerFactoryBean.packagesToScan = ['com.example.sakila']
    entityManagerFactoryBean.jpaVendorAdapter = new HibernateJpaVendorAdapter()
    entityManagerFactoryBean.jpaProperties = hibernatePsql10Properties()
    entityManagerFactoryBean
  }

  @Bean
  PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager()
    transactionManager.entityManagerFactory = entityManagerFactory
    transactionManager
  }

  @Bean
  PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslator() {
    new PersistenceExceptionTranslationPostProcessor()
  }

  private Properties hibernatePsql10Properties() {
    Properties properties = new Properties()
    properties.setProperty('hibernate.dialect', 'org.hibernate.dialect.PostgreSQL10Dialect')
    properties
  }
}
