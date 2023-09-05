package ru.felix.votingservice.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(path = "/{restaurantId}")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int restaurantId) {
        log.info("get restaurant - {} with menu from current date", restaurantId);
        return service.getWithDishes(restaurantId, LocalDate.now());
    }
}
