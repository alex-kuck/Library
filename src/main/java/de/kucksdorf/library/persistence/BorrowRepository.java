package de.kucksdorf.library.persistence;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.kucksdorf.library.persistence.util.BorrowCSV;
import de.kucksdorf.library.persistence.util.CSVReader;

import static de.kucksdorf.library.persistence.util.DateUtils.toDate;
import static java.util.stream.Collectors.toSet;

@Service
public class BorrowRepository implements IBorrowRepository {
    private final Set<BorrowEntity> borrows = new HashSet<>();

    public BorrowRepository() {
        try {
            CSVReader.readEntries(BorrowCSV.class, "borrowed.csv")
                    .stream()
                    .map(this::toEntity)
                    .forEach(borrows::add);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private BorrowEntity toEntity(BorrowCSV borrow) {
        return BorrowEntity.builder()
                .userName(borrow.getUserName())
                .bookName(borrow.getBookName())
                .borrowedFrom(toDate(borrow.getBorrowedFrom()))
                .borrowedUntil(toDate(borrow.getBorrowedUntil()))
                .build();
    }

    @Override
    public Collection<BorrowEntity> findAll() {
        return Collections.unmodifiableSet(borrows);
    }

    @Override
    public Collection<BorrowEntity> findFor(UserEntity user) {
        return borrows.stream()
                .filter(borrow -> borrow.belongsTo(user))
                .collect(toSet());
    }

    @Override
    public Collection<BorrowEntity> findActiveFor(UserEntity user) {
        return borrows.stream()
                .filter(borrow -> borrow.belongsTo(user) && borrow.isActive())
                .collect(toSet());
    }
}
