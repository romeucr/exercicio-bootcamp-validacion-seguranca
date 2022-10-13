package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<CityDTO> findAll() {
        List<City> allCities = cityRepository.findAll();
        return allCities.stream().map(CityDTO::new).collect(Collectors.toList());
    }

    public CityDTO insert(CityDTO dto) {
        City citySaved = cityRepository.save(dtoToEntity(dto));
        return new CityDTO(citySaved);
    }

    public City dtoToEntity(CityDTO dto) {
        return new City(dto.getId(), dto.getName());
    }
}
