package ru.felix.votingservice.web.restaurant;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProfileRestaurantController {

    private final RestaurantService service;

    private final Logger log = LoggerFactory.getLogger(ProfileRestaurantController.class);

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id) {
        log.info("get restaurant - {} with menu", id);
        return service.getWithDishes(id);
    }
}
