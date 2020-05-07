package com.example.sakila.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class JPAConfig {

  @Bean
  fun localConatinerEntityManagerFactoryBean(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
    val factoryBean = LocalContainerEntityManagerFactoryBean()

    factoryBean.dataSource = dataSource
    factoryBean.setPackagesToScan("com.example.sakila")
    factoryBean.jpaVendorAdapter = HibernateJpaVendorAdapter()
    factoryBean.setJpaProperties(hibernatePsql10Properties())

    return factoryBean
  }

  @Bean
  fun persistenceExceptionTranslationPostProcessor(): PersistenceExceptionTranslationPostProcessor =
      PersistenceExceptionTranslationPostProcessor()

  @Bean
  fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
    val transactionManager = JpaTransactionManager()

    transactionManager.entityManagerFactory = entityManagerFactory

    return transactionManager
  }

  private fun hibernatePsql10Properties(): Properties {
    val properties: Properties = Properties()
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect")
    return properties
  }
}
