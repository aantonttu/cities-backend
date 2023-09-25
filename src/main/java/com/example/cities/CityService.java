package com.example.cities;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@AllArgsConstructor
public class CityService {

    @Autowired
    private final CityRepository cityRepository;

    public List<Map<String, String>> findByPage(Long page) {
        List<Map<String, String>> r = new ArrayList<>();

        for (long i = (page * 9) - 8; i <= page * 9; i++) {
            Map<String, String> map = new HashMap<>();
            Optional<City> city = cityRepository.findById(i);
            if (city.isPresent()) {
                map.put("id", String.valueOf(city.get().getId()));
                map.put("name", String.valueOf(city.get().getName()));
                map.put("picture", String.valueOf(city.get().getPicture()));
                r.add(map);
            }
        }
        return r;
    }

    public List<Map<String, String>> findByName(String param) {
        List<Map<String, String>> r = new ArrayList<>();
        for (City city : cityRepository.findAll()) {
            if (city.getName().toLowerCase().contains(param.toLowerCase())) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(city.getId()));
                map.put("name", String.valueOf(city.getName()));
                map.put("picture", String.valueOf(city.getPicture()));
                r.add(map);
            }
        }
        return r;
    }

    public Map<String, String> findById(Long id) {
        Map<String, String> map = new HashMap<>();
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            map.put("id", String.valueOf(city.get().getId()));
            map.put("name", String.valueOf(city.get().getName()));
            map.put("picture", String.valueOf(city.get().getPicture()));
        }
        return map;
    }

    public void update(Long id, Map<String, String> data) {
        String name = data.get("name");
        String picture = data.get("picture");
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            city.get().setName(name);
            city.get().setPicture(picture);
            cityRepository.save(city.get());
        }
    }
}
