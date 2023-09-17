package ru.felix.votingservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "of_date"}))
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @Column(name = "of_date", nullable = false)
    private LocalDate localDate;

    @Column(name = "price", nullable = false)
    private int price;

    public Dish(Integer id, String name, LocalDate localDate, int price) {
        super(id, name);
        this.localDate = localDate;
        this.price = price;
    }
}
