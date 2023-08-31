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

import java.time.LocalDate;
import java.util.List;

import static ru.felix.votingservice.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishService {

    private final DishRepository dishRepository;

    private final RestaurantRepository restaurantRepository;

    public List<Dish> getAllByRestaurant(int restaurantId, LocalDate date) {
        return dishRepository.findAllByRestaurantId(restaurantId, date);
    }

    public Dish getByRestaurant(int restaurantId, int dishId) {
        return checkNotFoundWithId(dishRepository.findByRestaurantIdAndId(restaurantId, dishId), dishId);
    }

    @Transactional
    public Dish create(int restaurantId, Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        Restaurant restaurant =
                ValidationUtil.checkNotFoundWithId(restaurantRepository.findById(restaurantId)
                        .orElse(null), restaurantId);
        dish.setRestaurant(restaurant);
        dish.setLocalDate(LocalDate.now());
        return dishRepository.save(dish);
    }

    @Transactional
    public void update(int restaurantId, int dishId, Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        Dish updateDish = getByRestaurant(restaurantId, dishId);
        updateDish.setPrice(dish.getPrice());
        updateDish.setName(dish.getName());
        dishRepository.save(updateDish);
    }

    public void delete(int restaurantId, int dishId) {
        checkNotFoundWithId(dishRepository.deleteByRestaurantIdAndId(restaurantId, dishId), dishId);
    }
}
