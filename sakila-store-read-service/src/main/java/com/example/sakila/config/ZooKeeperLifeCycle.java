package com.example.sakila.config;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ZooKeeperLifeCycle {

  private final Logger log = LoggerFactory.getLogger(ZooKeeperLifeCycle.class);

  private final CuratorFramework curatorFramework;

  @Autowired
  public ZooKeeperLifeCycle(CuratorFramework curatorFramework) {
    this.curatorFramework = curatorFramework;
  }

  @PostConstruct
  public void init() {
    log.info("Starting Curator Framework");
    curatorFramework.start();
  }

  @PreDestroy
  public void destroy() {
    log.info("Stopping Curator Framework");
    curatorFramework.close();
  }
}
