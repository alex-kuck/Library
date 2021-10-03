package de.kucksdorf.library.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {
    public static final LocalDate TODAY = LocalDate.of(2021, 2, 3);

    @BeforeAll
    static void beforeAll() {
        DateUtils.setClock(TODAY);
    }

    @AfterAll
    static void afterAll() {
        DateUtils.useDefaultClock();
    }

    @Test
    void isActiveRange() {
        assertThat(DateUtils.isActiveRange(TODAY, TODAY.plusDays(1))).isTrue();
        assertThat(DateUtils.isActiveRange(TODAY.minusDays(2), TODAY.minusDays(1))).isFalse();
    }

    @ParameterizedTest
    @ArgumentsSource(IsContainedArguments.class)
    void isContained(LocalDate date, LocalDate start, LocalDate end, boolean isContained) {
        assertThat(DateUtils.isContained(date, start, end)).isEqualTo(isContained);
    }
}

class IsContainedArguments implements ArgumentsProvider{
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(DateUtilsTest.TODAY, DateUtilsTest.TODAY.minusDays(1), DateUtilsTest.TODAY.plusDays(1), true),
                Arguments.of(DateUtilsTest.TODAY, DateUtilsTest.TODAY.minusDays(1), DateUtilsTest.TODAY, true),
                Arguments.of(DateUtilsTest.TODAY, DateUtilsTest.TODAY, null, true),
                Arguments.of(DateUtilsTest.TODAY, null, null, false)
        );
    }
}