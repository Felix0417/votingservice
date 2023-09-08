package ru.felix.votingservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.felix.votingservice.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.localDate=CURRENT_DATE")
    Optional<Vote> getByIdOnCurrentDate(@Param("userId") int id);

    @Query("SELECT v.restaurant.id FROM Vote v WHERE v.localDate=:date GROUP BY v.restaurant.id ORDER BY COUNT(v.restaurant.id) DESC LIMIT 1")
    Integer getWinnerFromDate(@Param("date") LocalDate date);
}
