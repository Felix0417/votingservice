package ru.felix.votingservice.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.felix.votingservice.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
