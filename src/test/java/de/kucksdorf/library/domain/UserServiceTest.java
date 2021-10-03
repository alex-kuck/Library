package de.kucksdorf.library.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import de.kucksdorf.library.api.User;
import de.kucksdorf.library.domain.enums.Gender;
import de.kucksdorf.library.persistence.BorrowEntity;
import de.kucksdorf.library.persistence.IBorrowRepository;
import de.kucksdorf.library.persistence.IUserRepository;
import de.kucksdorf.library.persistence.UserEntity;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService sut;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private IBorrowRepository borrowRepository;

    private final UserEntity userInactive = UserEntity.builder()
            .firstName("Han")
            .lastName("Solo")
            .gender(Gender.MALE)
            .memberSince(LocalDate.of(1980, 6, 1))
            .memberUntil(LocalDate.of(2019, 1, 1))
            .build();
    private final UserEntity userActiveWithBorrow = UserEntity.builder()
            .firstName("Luke")
            .lastName("Skywalker")
            .gender(Gender.MALE)
            .build();
    private final UserEntity userActiveWithoutBorrow = UserEntity.builder()
            .firstName("Leia")
            .lastName("Organa")
            .gender(Gender.FEMALE)
            .memberSince(LocalDate.of(2020, 1, 1))
            .build();
    private final BorrowEntity borrow1 = BorrowEntity.builder()
            .userName(userInactive.getLastName() + "," + userInactive.getFirstName())
            .build();
    private final BorrowEntity borrow2 = BorrowEntity.builder()
            .userName(userActiveWithBorrow.getLastName() + ", " + userActiveWithBorrow.getFirstName())
            .build();

    @Test
    void findUsersWhoBorrowedAtLeastOneBook() {
        when(borrowRepository.findAll()).thenReturn(Set.of(borrow1, borrow2));
        when(userRepository.findAll()).thenReturn(Set.of(userInactive, userActiveWithoutBorrow, userActiveWithBorrow));
        assertThat(sut.findUsersWhoBorrowedAtLeastOneBook()).containsExactlyInAnyOrderElementsOf(resultOf(userInactive, userActiveWithBorrow));
    }

    private Set<User> resultOf(UserEntity... users) {
        return Stream.of(users)
                .map(this::entityToApi)
                .collect(toSet());
    }

    private User entityToApi(UserEntity userEntity) {
        return User.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .gender(userEntity.getGender())
                .memberSince(userEntity.getMemberSince())
                .memberUntil(userEntity.getMemberUntil())
                .build();
    }

    @Test
    void findUsersWhoAreActiveAndBorrowNothing() {
        when(userRepository.findActive()).thenReturn(Set.of(userActiveWithBorrow, userActiveWithoutBorrow));
        when(borrowRepository.findActiveFor(eq(userActiveWithBorrow))).thenReturn(Set.of(borrow1));
        when(borrowRepository.findActiveFor(eq(userActiveWithoutBorrow))).thenReturn(Set.of());
        assertThat(sut.findUsersWhoAreActiveAndBorrowNothing()).containsExactlyInAnyOrderElementsOf(resultOf(userActiveWithoutBorrow));
    }
}