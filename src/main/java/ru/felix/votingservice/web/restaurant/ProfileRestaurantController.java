package ru.felix.votingservice.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class ProfileRestaurantController {

    static final String REST_URL = "/api/user/restaurant";

    private final RestaurantService service;

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
