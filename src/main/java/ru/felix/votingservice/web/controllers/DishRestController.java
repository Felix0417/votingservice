package ru.felix.votingservice.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.service.DishService;

import java.util.List;

@RestController
@RequestMapping(value = "/dish", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DishRestController {

    private final DishService service;

    private static final Logger log = LoggerFactory.getLogger(DishRestController.class);

    @GetMapping
    public List<Dish> getAll(){
        log.info("get all Dishes");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id){
        log.info("get Dish by id - {}", id);
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid @RequestBody Dish dish){
        if (dish.isNew()){
            log.info("create new Dish - {}", dish);
            service.create(dish);
        }else{
            log.info("update Dish - {}", dish);
            service.update(dish);
        }
        ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        log.info("delete Dish with id - {}", id);
        service.delete(id);
    }
}
