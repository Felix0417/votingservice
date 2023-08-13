package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.repository.DishRepository;

import java.util.List;

import static ru.felix.votingservice.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository repository;

    public List<Dish> getAll(){
        return repository.findAll();
    }

    public Dish get(int id){
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public Dish create(Dish dish){
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    public void update(Dish dish){
        Assert.notNull(dish, "dish must not be null");
        repository.save(dish);
    }

    public void delete(int id){
        checkNotFoundWithId(repository.delete(id), id);
    }
}
