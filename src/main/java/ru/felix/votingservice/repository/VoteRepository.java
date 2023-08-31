package ru.felix.votingservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.felix.votingservice.model.Vote;

import java.time.LocalDate;

public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.localDate=:currentDate")
    Vote getByIdOnCurrentDate(@Param("userId") int id, @Param("currentDate") LocalDate localDate);

    @Query("select v.restaurant.id from Vote v WHERE v.localDate=:date GROUP BY v.restaurant.id ORDER BY COUNT(v.restaurant.id) DESC LIMIT 1")
    Integer getWinnerFromDate(@Param("date") LocalDate date);
}
