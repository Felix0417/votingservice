package ru.felix.votingservice.web.restaurant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.service.RestaurantService;

import java.util.List;

import static ru.felix.votingservice.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = "api/admin/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminRestaurantController {

    private final RestaurantService service;

    private final Logger log = LoggerFactory.getLogger(AdminRestaurantController.class);

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return service.getAll();
    }

    @GetMapping(path = "/{restaurantId}")
    public ResponseEntity<Restaurant> get(@PathVariable int restaurantId) {
        log.info("get restaurant - {}", restaurantId);
        return service.getWithDishes(restaurantId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(@Valid @RequestBody Restaurant restaurant) {
        if (restaurant.isNew()) {
            log.info("create new restaurant - {}", restaurant);
            service.create(restaurant);
        }
    }

    @PutMapping("/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int restaurantId) {
        log.info("update restaurant - {}", restaurant);
        assureIdConsistent(restaurant, restaurantId);
        service.update(restaurant);
    }

    @DeleteMapping("/{restaurantId}")
    public void delete(@PathVariable int restaurantId) {
        log.info("delete restaurant - {} ", restaurantId);
        service.delete(restaurantId);
    }
}