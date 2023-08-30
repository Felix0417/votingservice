package ru.felix.votingservice.to;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class DishTo extends NamedTo {

    @Positive
    private int price;

    @NonNull
    private int restaurantId;

    public DishTo(Integer id, String name) {
        super(id, name);
    }
}
