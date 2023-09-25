package com.example.cities;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(maxAge = 3600)
@RequestMapping("cities")
@RestController
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("{page}")
    public List<Map<String, String>> getCities(@PathVariable Long page) {
        return cityService.findByPage(page);
    }

    @GetMapping("/name/{param}")
    public List<Map<String, String>> getCitiesByName(@PathVariable String param) {
        return cityService.findByName(param);
    }

    @GetMapping("/city/{id}")
    public Map<String, String> getCity(@PathVariable Long id) {
        return cityService.findById(id);
    }

    @PutMapping("/city/{id}/update")
    public void updateCity(@PathVariable Long id, @RequestBody Map<String, String> data) {
        cityService.update(id, data);
    }
}