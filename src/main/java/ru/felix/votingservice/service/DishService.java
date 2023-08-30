package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.repository.DishRepository;
import ru.felix.votingservice.repository.RestaurantRepository;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.util.List;

import static ru.felix.votingservice.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishService {

    private final DishRepository dishRepository;

    private final RestaurantRepository restaurantRepository;

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    public Dish get(int id) {
        return checkNotFoundWithId(dishRepository.findById(id).orElse(null), id);
    }

    @Transactional
    public void create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        int restaurantId = dish.getRestaurant().id();
        Restaurant restaurant =
                ValidationUtil.checkNotFoundWithId(restaurantRepository.findById(restaurantId)
                        .orElse(null), restaurantId);
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
    }

    @Transactional
    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");

        dishRepository.save(dish);
    }

    public void delete(int id) {
        checkNotFoundWithId(dishRepository.delete(id), id);
    }
}
