package com.example.sakila.module.city;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.city.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

  private final CityRepository cityRepository;

  @Autowired
  public CityService(CityRepository cityRepository) {
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

  public City addNewCity(City city) {
    return cityRepository.insertCity(city);
  }

  public City updateCity(Long id, City city) {
    City target = cityRepository.getCityById(id);
    if (target == null) throw new NotFoundException("Target city for update does not exist");

    target.setCity(city.getCity());
    target.setCountry(city.getCountry());

    return cityRepository.updateCity(target);
  }

  public void deleteCity(Long id) {
    try {
      cityRepository.deleteCity(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
