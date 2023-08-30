package ru.felix.votingservice.web.dish;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.service.DishService;

import java.util.List;

import static ru.felix.votingservice.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = "/api/admin/dish")
@RequiredArgsConstructor
public class AdminDishController {

    private final DishService service;

    private static final Logger log = LoggerFactory.getLogger(AdminDishController.class);

    @GetMapping
    public List<Dish> getAll() {
        log.info("get all Dishes");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get Dish by id - {}", id);
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid @RequestBody Dish dish) {
        if (dish.isNew()) {
            log.info("create new Dish - {}", dish);
            service.create(dish);
        }
    }

    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("update Dish - {}", dish);
        assureIdConsistent(dish, id);
        service.update(dish);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete dish with id - {}", id);
        service.delete(id);
    }
}
