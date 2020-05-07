package com.example.sakila.discovery


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceInstanceController @Autowired constructor(
    @Value("\${spring.application.name}") private val applicationName: String,
    private val discoveryClient: DiscoveryClient
) {

  @RequestMapping(value = ["/service-instances"], method = [RequestMethod.GET])
  fun getServiceInstances(): List<ServiceInstance> {
    return discoveryClient.getInstances(applicationName)
  }
}
