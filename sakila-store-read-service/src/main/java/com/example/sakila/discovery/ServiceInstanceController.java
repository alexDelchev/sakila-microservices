package com.example.sakila.discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceInstanceController {

  @Value("${spring.application.name}")
  private String applicationName;

  private final DiscoveryClient discoveryClient;

  @Autowired
  public ServiceInstanceController(DiscoveryClient discoveryClient) {
    this.discoveryClient = discoveryClient;
  }

  @RequestMapping(value = "/service-instances", method = RequestMethod.GET)
  public List<ServiceInstance> getServiceInstancesByApplicationName() {
    return discoveryClient.getInstances(applicationName);
  }
}
