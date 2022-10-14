package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CityRepository cityRepository;

    public Page<EventDTO> findAllPaged(Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        return events.map(EventDTO::new);
    }

    public EventDTO insert(EventDTO eventDTO) {
        Event event = dtoToEntity(eventDTO);
        Event eventDB = eventRepository.save(event);
        return new EventDTO(eventDB.getId(), eventDB.getName(), eventDB.getDate(), eventDB.getUrl(), eventDB.getCity().getId());
    }

    private Event dtoToEntity(EventDTO dto) {
        Optional<City> optCity = cityRepository.findById(dto.getCityId());

        if (optCity.isPresent()) {
            return new Event(null, dto.getName(), dto.getDate(), dto.getUrl(), optCity.get());
        } else {
            throw new NoSuchElementException("A cidade informada nao foi encontrada.");
        }
    }
}
