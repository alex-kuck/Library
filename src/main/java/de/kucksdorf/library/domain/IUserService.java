package de.kucksdorf.library.domain;

import java.time.LocalDate;
import java.util.Collection;

import de.kucksdorf.library.api.User;

public interface IUserService {
    Collection<User> findUsersWhoBorrowedAtLeastOneBook();
    Collection<User> findUsersWhoAreActiveAndBorrowNothing();
    Collection<User> findUsersWithBorrowOn(LocalDate date);
}
