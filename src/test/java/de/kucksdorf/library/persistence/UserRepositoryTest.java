package de.kucksdorf.library.persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;

import de.kucksdorf.library.utils.DateUtils;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepository();

    @BeforeAll
    static void beforeAll() {
        DateUtils.setClock(LocalDate.of(2020, 1, 1));
    }

    @AfterAll
    static void afterAll() {
        DateUtils.useDefaultClock();
    }

    @Test
    void findAll() {
        Collection<UserEntity> users = userRepository.findAll();
        assertThat(users).hasSize(11);
        assertThat(users.stream()
                .filter(it -> it.getLastName()
                        .equals("Zhungwang"))).hasSize(2);
    }

    @Test
    void findByUsername() {
        assertThat(userRepository.findBy("Zhungwang,Noah")).isPresent();
        assertThat(userRepository.findBy("Skywalker,Luke")).isEmpty();
    }

    @Test
    void findActive() {
        assertThat(userRepository.findActive()).hasSize(8);
    }
}