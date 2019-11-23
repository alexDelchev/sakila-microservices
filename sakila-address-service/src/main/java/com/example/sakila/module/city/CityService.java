package com.example.sakila.module.city;

import com.example.sakila.module.city.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
