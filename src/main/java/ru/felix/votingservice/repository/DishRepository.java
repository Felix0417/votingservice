package ru.felix.votingservice.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.felix.votingservice.model.Dish;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
}
