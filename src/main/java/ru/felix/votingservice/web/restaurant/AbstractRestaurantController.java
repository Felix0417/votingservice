package ru.felix.votingservice.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRestaurantController {

    protected final RestaurantService service;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id) {
        log.info("get restaurant - {} with menu from current date", id);
        return service.getWithDishes(id, LocalDate.now());
    }
}
