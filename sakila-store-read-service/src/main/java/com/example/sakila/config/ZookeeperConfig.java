package com.example.sakila.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {

  private static final int TIMEOUT = 10_000;

  @Value("${zookeeper.url}")
  private String host;

  @Bean
  public CuratorFramework curatorFramework() {
    return CuratorFrameworkFactory
        .builder()
        .retryPolicy(new ExponentialBackoffRetry(1000, 3))
        .connectString(host)
        .connectionTimeoutMs(TIMEOUT)
        .sessionTimeoutMs(TIMEOUT)
        .build();
  }
}
