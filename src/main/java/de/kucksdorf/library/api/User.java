package de.kucksdorf.library.api;

import java.time.LocalDate;

import de.kucksdorf.library.domain.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate memberSince;
    private LocalDate memberUntil;
}
