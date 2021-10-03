package de.kucksdorf.library.persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import de.kucksdorf.library.utils.DateUtils;

import static org.assertj.core.api.Assertions.assertThat;

class BorrowRepositoryTest {
    private static final LocalDate TODAY = LocalDate.of(2021, 6, 6);

    private final BorrowRepository borrowRepository = new BorrowRepository();

    @BeforeAll
    static void beforeAll() {
        DateUtils.setClock(TODAY);
    }

    @AfterAll
    static void afterAll() {
        DateUtils.useDefaultClock();
    }

    @Test
    void findAll() {
        assertThat(borrowRepository.findAll()).hasSize(118);
    }

    @Test
    void findForUser() {
        assertThat(borrowRepository.findFor(UserEntity.builder().lastName("Zhungwang").firstName("Ava").build())).hasSize(12);
    }

    @Test
    void findActiveForUser() {
        assertThat(borrowRepository.findActiveFor(UserEntity.builder()
                .firstName("Ava")
                .lastName("Zhungwang")
                .build())).hasSize(3);
    }
}