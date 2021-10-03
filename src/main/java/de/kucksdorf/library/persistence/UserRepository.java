package de.kucksdorf.library.persistence;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import de.kucksdorf.library.domain.enums.Gender;
import de.kucksdorf.library.persistence.util.CSVReader;
import de.kucksdorf.library.persistence.util.DateUtils;
import de.kucksdorf.library.persistence.util.UserCSV;

import static java.util.stream.Collectors.toSet;

@Service
public class UserRepository implements IUserRepository {
    private final Set<UserEntity> users = new HashSet<>();

    public UserRepository() {
        try {
            CSVReader.readEntries(UserCSV.class, "user.csv")
                    .stream()
                    .map(this::toEntity)
                    .forEach(users::add);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<UserEntity> findAll() {
        return Collections.unmodifiableSet(users);
    }

    @Override
    public Optional<UserEntity> findBy(String userName) {
        return users.stream()
                .filter(it -> String.format("%s,%s", it.getLastName(), it.getFirstName())
                        .equals(userName))
                .findFirst();
    }

    @Override
    public Collection<UserEntity> findActive() {
        return findAll().stream()
                .filter(UserEntity::isActive)
                .collect(toSet());
    }

    private UserEntity toEntity(UserCSV user) {
        return UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(gender(user.getGender()))
                .memberSince(DateUtils.toDate(user.getMemberSince()))
                .memberUntil(DateUtils.toDate(user.getMemberUntil()))
                .build();
    }

    private Gender gender(String gender) {
        return Optional.ofNullable(gender)
                .map(String::toLowerCase)
                .map(it -> {
                    switch (it) {
                        case "f":
                            return Gender.FEMALE;
                        case "m":
                            return Gender.MALE;
                        case "d":
                            return Gender.DIVERSE;
                    }
                    return null;
                })
                .orElse(null);
    }
}
