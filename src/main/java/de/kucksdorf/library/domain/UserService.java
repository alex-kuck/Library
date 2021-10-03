package de.kucksdorf.library.domain;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import de.kucksdorf.library.api.User;
import de.kucksdorf.library.persistence.BorrowEntity;
import de.kucksdorf.library.persistence.IBorrowRepository;
import de.kucksdorf.library.persistence.IUserRepository;
import de.kucksdorf.library.persistence.UserEntity;
import de.kucksdorf.library.utils.DateUtils;
import lombok.RequiredArgsConstructor;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IBorrowRepository borrowRepository;

    @Override
    public Collection<User> findUsersWhoBorrowedAtLeastOneBook() {
        Predicate<UserEntity> userHasBorrowed = hasBorrowedChecker(borrowRepository.findAll());
        return responseOf(userRepository.findAll()
                .stream()
                .filter(userHasBorrowed));
    }

    private Predicate<UserEntity> hasBorrowedChecker(Collection<BorrowEntity> borrows) {
        return user -> borrows.stream()
                .anyMatch(borrow -> borrow.belongsTo(user));
    }

    private Set<User> responseOf(Stream<UserEntity> users) {
        return users.map(this::toApiModel)
                .collect(toSet());
    }

    private User toApiModel(UserEntity userEntity) {
        return User.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .gender(userEntity.getGender())
                .memberSince(userEntity.getMemberSince())
                .memberUntil(userEntity.getMemberUntil())
                .build();
    }

    @Override
    public Collection<User> findUsersWhoAreActiveAndBorrowNothing() {
        return responseOf(userRepository.findActive()
                .stream()
                .filter(not(this::hasActiveBorrow)));
    }

    private boolean hasActiveBorrow(UserEntity user) {
        return !borrowRepository.findActiveFor(user)
                .isEmpty();
    }

    @Override
    public Collection<User> findUsersWithBorrowOn(LocalDate date) {
        Predicate<UserEntity> hasBorrowOnDate = hasBorrowOnDateChecker(date);
        return responseOf(userRepository.findAll()
                .stream()
                .filter(hasBorrowOnDate));
    }

    private Predicate<UserEntity> hasBorrowOnDateChecker(LocalDate date) {
        return user -> borrowRepository.findFor(user)
                .stream()
                .anyMatch(borrow -> DateUtils.isContained(date, borrow.getBorrowedFrom(), borrow.getBorrowedUntil()));
    }
}
