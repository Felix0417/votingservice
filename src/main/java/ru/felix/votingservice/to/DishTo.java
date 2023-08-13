package ru.felix.votingservice.to;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishTo extends NamedTo{

    @Column(name = "price", nullable = false)
    private int price;

    public DishTo(Integer id, String name) {
        super(id, name);
    }
}
