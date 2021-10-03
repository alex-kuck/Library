package de.kucksdorf.library.persistence;

import java.time.LocalDate;

import de.kucksdorf.library.domain.enums.Gender;
import de.kucksdorf.library.utils.DateUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserEntity {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private Gender gender;
    private LocalDate memberSince;
    private LocalDate memberUntil;

    public String getFullName() {
        return lastName + ", " + firstName;
    }

    public boolean isActive() {
        return DateUtils.isActiveRange(memberSince, memberUntil);
    }
}
