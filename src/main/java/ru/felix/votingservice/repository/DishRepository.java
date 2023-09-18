package ru.felix.votingservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.felix.votingservice.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.id=:dishId AND d.localDate=CURRENT_DATE ")
    Optional<Dish> getByRestaurantId(@Param("restaurantId") int restaurantId, @Param("dishId") int dishId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.id=:dishId")
    int deleteByRestaurantIdAndId(@Param("restaurantId") int restaurantId, @Param("dishId") int dishId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.localDate=:localDate")
    List<Dish> getAllByRestaurantFromDate(@Param("restaurantId") int restaurantId, @Param("localDate") LocalDate localDate);
}
