package com.example.sakila.module.city;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.city.event.CityEventUtils;
import com.example.sakila.module.city.event.model.CityCreatedEvent;
import com.example.sakila.module.city.event.model.CityEventDTO;
import com.example.sakila.module.city.event.model.CityUpdatedEvent;
import com.example.sakila.module.city.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

  private final EventBus eventBus;

  private final CityRepository cityRepository;

  @Autowired
  public CityService(@Qualifier("CityEventBus") EventBus eventBus,  CityRepository cityRepository) {
    this.eventBus = eventBus;
    this.cityRepository = cityRepository;
  }

  public City getCityById(Long id) {
    if (id == null) return null;
    return cityRepository.getCityById(id);
  }

  public City getCityByAddressId(Long addressId) {
   if (addressId == null) return null;
   return cityRepository.getCityByAddressId(addressId);
  }

  public List<City> getCitiesByCountry(Long countryId) {
    if (countryId == null) return null;
    return cityRepository.getCitiesByCountry(countryId);
  }

  public List<City> getAllCities() {
    return cityRepository.getAllCities();
  }

  private void generateCreatedEvent(City city) {
    CityEventDTO dto = CityEventUtils.toDto(city);
    CityCreatedEvent event = new CityCreatedEvent();
    event.setCityEventDTO(dto);
    eventBus.emit(event);
  }

  public City createCity(City city) {
    City result = cityRepository.insertCity(city);

    generateCreatedEvent(city);

    return result;
  }

  private void generatedUpdatedEvent(City city) {
    CityEventDTO dto = CityEventUtils.toDto(city);
    CityUpdatedEvent event = new CityUpdatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  public City updateCity(Long id, City city) {
    City target = cityRepository.getCityById(id);
    if (target == null) throw new NotFoundException("Target city for update does not exist");

    target.setCity(city.getCity());
    target.setCountry(city.getCountry());

    City result = cityRepository.updateCity(target);

    generatedUpdatedEvent(result);

    return result;
  }

  public void deleteCity(Long id) {
    City city = cityRepository.getCityById(id);
    if (city == null) throw new NotFoundException("City for ID " + id + " does not exist");

    try {
      cityRepository.deleteCity(city);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
