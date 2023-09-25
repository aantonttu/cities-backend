package com.example.cities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
//    List<City> findAllById(Long page);
}