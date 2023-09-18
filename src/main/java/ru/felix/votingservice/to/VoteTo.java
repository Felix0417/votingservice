package ru.felix.votingservice.to;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class VoteTo {

    private Integer userId;

    private Integer restaurantId;

    private LocalDate localDate;

    public VoteTo(VoteTo voteTo) {
        this(voteTo.userId, voteTo.restaurantId, voteTo.getLocalDate());
    }
}
