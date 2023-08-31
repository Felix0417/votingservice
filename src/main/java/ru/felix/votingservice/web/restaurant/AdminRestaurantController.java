package ru.felix.votingservice.web.restaurant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.service.RestaurantService;

import java.net.URI;
import java.util.List;

import static ru.felix.votingservice.util.validation.ValidationUtil.assureIdConsistent;
import static ru.felix.votingservice.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminRestaurantController {

    static final String REST_URL = "/api/admin/restaurant";

    private final RestaurantService service;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create new restaurant - {}", restaurant);
        Restaurant newRestaurant = service.create(restaurant);
        URI restaurantUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}").buildAndExpand(newRestaurant.id()).toUri();
        return ResponseEntity.created(restaurantUri).body(newRestaurant);
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