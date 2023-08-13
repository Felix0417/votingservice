package ru.felix.votingservice.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final RestaurantService service;


    @GetMapping
    public List<Restaurant> getAll(){
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    public Restaurant get(@PathVariable int id){
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid @RequestBody Restaurant restaurant){
        if (restaurant.isNew()){
            service.create(restaurant);
        }else{
            service.update(restaurant);
        }
        ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
