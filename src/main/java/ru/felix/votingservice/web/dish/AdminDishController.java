package ru.felix.votingservice.web.dish;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.service.DishService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.felix.votingservice.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminDishController {
    static final String REST_URL = "/api/admin/restaurants";

    private final DishService service;

    @GetMapping("/{restaurantId}/dishes")
    public List<Dish> getAllByRestaurantAndDate(@PathVariable int restaurantId, @RequestParam LocalDate localDate) {
        return service.getAllByRestaurantFromDate(restaurantId, localDate);
    }

    @GetMapping("/{restaurantId}/dishes/{dishId}")
    public Dish getByRestaurant(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
        log.info("get dish - {} from restaurant - {}", dishId, restaurantId);
        return service.getByRestaurantIdAndId(restaurantId, dishId);
    }

    @PostMapping(value = "/{restaurantId}/dishes")
    public ResponseEntity<Dish> create(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        checkNew(dish);
        log.info("create new Dish - {}", dish);
        Dish newDish = service.create(restaurantId, dish);
        URI newDishUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newDish.getId()).toUri();
        return ResponseEntity.created(newDishUri).body(newDish);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @PathVariable int dishId, @Valid @RequestBody Dish dish) {
        log.info("update dish - {} with id - {} from restaurant - {}", dish, dishId, restaurantId);
        service.update(restaurantId, dishId, dish);
    }

    @DeleteMapping("/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int dishId) {
        log.info("delete dish with id - {} from restaurant - {}", dishId, restaurantId);
        service.delete(restaurantId, dishId);
    }
}
