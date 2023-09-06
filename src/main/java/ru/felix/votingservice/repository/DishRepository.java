package ru.felix.votingservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.felix.votingservice.model.Dish;

import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.id=:dishId AND d.localDate=CURRENT_DATE ")
    Optional<Dish> findByRestaurantIdAndId(@Param("restaurantId") int restaurantId, @Param("dishId") int dishId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.id=:dishId")
    int deleteByRestaurantIdAndId(@Param("restaurantId") int restaurantId, @Param("dishId") int dishId);
}
