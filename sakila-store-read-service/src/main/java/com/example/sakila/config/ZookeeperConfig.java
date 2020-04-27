package com.example.sakila.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {

  private static final String HOST = "localhost:2181";

  private static final int TIMEOUT = 10_000;

  @Bean
  public CuratorFramework curatorFramework() {
    return CuratorFrameworkFactory
        .builder()
        .retryPolicy(new ExponentialBackoffRetry(1000, 3))
        .connectString(HOST)
        .connectionTimeoutMs(TIMEOUT)
        .sessionTimeoutMs(TIMEOUT)
        .build();
  }
}
