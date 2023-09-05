package ru.felix.votingservice.to;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private Integer restaurantId;

    private LocalDate localDate;

    public VoteTo(VoteTo voteTo) {
        this.userId = voteTo.userId;
        this.restaurantId = voteTo.restaurantId;
        this.localDate = voteTo.getLocalDate();
    }
}
