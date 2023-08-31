package ru.felix.votingservice.to;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VoteTo extends BaseTo {

    @NotBlank
    private Integer restaurantId;

    private LocalDate localDate;

    public VoteTo(Integer id, Integer restaurantId, LocalDate localDate) {
        super(id);
        this.restaurantId = restaurantId;
        this.localDate = localDate;
    }
}
