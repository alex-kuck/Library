package de.kucksdorf.library.persistence;

import java.util.Collection;

public interface IBorrowRepository {
    Collection<BorrowEntity> findAll();
    Collection<BorrowEntity> findFor(UserEntity user);
    Collection<BorrowEntity> findActiveFor(UserEntity user);
}
