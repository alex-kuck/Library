package de.kucksdorf.library.persistence;

import java.util.Collection;
import java.util.Optional;

public interface IUserRepository {
    Collection<UserEntity> findAll();

    Optional<UserEntity> findBy(String userName);

    Collection<UserEntity> findActive();
}
