package com.example.sakila.module.city

import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.DataConflictException
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.city.event.CityEventUtils
import com.example.sakila.module.city.event.model.CityCreatedEvent
import com.example.sakila.module.city.event.model.CityDeletedEvent
import com.example.sakila.module.city.event.model.CityEventDTO
import com.example.sakila.module.city.event.model.CityUpdatedEvent
import com.example.sakila.module.city.repository.CityRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Slf4j
@Service
class CityService {

  private final EventBus eventBus

  private final CityRepository cityRepository

  @Autowired
  CityService(@Qualifier('CityEventBus') EventBus eventBus,  CityRepository cityRepository) {
    this.eventBus = eventBus
    this.cityRepository = cityRepository
  }

  City getCityById(Long id) {
    if (!id) return null
    cityRepository.getCityById(id)
  }

  City getCityByAddressId(Long addressId) {
    if (!addressId) return null
    cityRepository.getCityByAddressId(addressId)
  }

  List<City> getCitiesByCountry(Long countryId) {
    if (!countryId) return null
    cityRepository.getCitiesByCountry(countryId)
  }

  List<City> getAllCities() {
    cityRepository.getAllCities()
  }

  private void generateCreatedEvent(City city) {
    CityEventDTO dto = CityEventUtils.toDto(city)
    CityCreatedEvent event = new CityCreatedEvent()
    event.cityEventDTO = dto
    eventBus.emit(event)
  }

  City createCity(City city) {
    log.info("Creating city")
    City result = cityRepository.insertCity(city)
    log.info("Created city id: ${result.id}")

    generateCreatedEvent(city)

    result
  }

  private void generatedUpdatedEvent(City city) {
    CityEventDTO dto = CityEventUtils.toDto(city)
    CityUpdatedEvent event = new CityUpdatedEvent()
    event.dto = dto
    eventBus.emit(event)
  }

  City updateCity(Long id, City city) {
    City target = cityRepository.getCityById(id)
    if (!target) throw new NotFoundException("Target city for update does not exist")
    log.info("Updateing city (ID: ${id})")

    target.city = city.city
    target.country = city.country

    City result = cityRepository.updateCity(target)

    generatedUpdatedEvent(result)

    result
  }

  private void generateDeletedEvent(Long id) {
    CityDeletedEvent event = new CityDeletedEvent(
        cityId: id
    )

    eventBus.emit(event)
  }

  void deleteCity(Long id) {
    City city = cityRepository.getCityById(id)
    if (!city) throw new NotFoundException("City for ID ${id} does not exist")
    log.info("Deleteing city (ID: ${id}")

    try {
      cityRepository.deleteCity(city)
      generateDeletedEvent(id)
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e)
    }
  }
}
