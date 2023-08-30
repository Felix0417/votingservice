package ru.felix.votingservice.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteTo {

    @Nullable
    private Integer userId;

    @NotBlank
    private Integer restaurantId;

    @Nullable
    private LocalDateTime localDateTime;
}
