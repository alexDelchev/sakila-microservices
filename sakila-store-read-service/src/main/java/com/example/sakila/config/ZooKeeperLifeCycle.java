package com.example.sakila.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ZooKeeperLifeCycle {

  private final CuratorFramework curatorFramework;

  @Autowired
  public ZooKeeperLifeCycle(CuratorFramework curatorFramework) {
    this.curatorFramework = curatorFramework;
  }

  @PostConstruct
  public void init() {
    curatorFramework.start();
  }

  @PreDestroy
  public void destroy() {
    curatorFramework.close();
  }
}
