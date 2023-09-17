package ru.felix.votingservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "voting_date"}))
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Vote extends BaseEntity implements Serializable {

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "voting_date", nullable = false)
    private LocalDate localDate;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    private boolean enabled;

    public Vote(Integer id, User user, LocalDate localDate, Restaurant restaurant, boolean enabled) {
        super(id);
        this.user = user;
        this.localDate = localDate;
        this.restaurant = restaurant;
        this.enabled = enabled;
    }

    public Vote(Vote vote) {
        this(vote.id, vote.user, vote.localDate, vote.restaurant, vote.enabled);
    }
}
