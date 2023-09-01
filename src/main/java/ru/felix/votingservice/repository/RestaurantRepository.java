package ru.felix.votingservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.felix.votingservice.model.Restaurant;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE r.id=:restaurantId AND d.localDate=:date")
    Optional<Restaurant> getWithDishes(@Param("restaurantId") int restaurantId, @Param("date") LocalDate localDate);
}
