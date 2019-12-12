package com.example.sakila.module.city.repository;

import com.example.sakila.module.city.City;

import java.util.List;

public interface CityRepository {

  City getCityById(Long id);

  City getCityByAddressId(Long addressId);

  List<City> getCitiesByCountry(Long cityId);

  List<City> getAllCities();

  City insertCity(City city);

  City updateCity(City city);

  void deleteCity(City city);
}
