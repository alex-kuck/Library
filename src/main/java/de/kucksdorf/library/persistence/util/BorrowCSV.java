package de.kucksdorf.library.persistence.util;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;

import lombok.Getter;

@Getter
public class BorrowCSV extends CSVBean {
    @CsvBindByPosition(position = 0)
    private String userName;
    @CsvBindByPosition(position = 1)
    private String bookName;
    @CsvBindByPosition(position = 2)
    @CsvDate("dd/MM/yyyy")
    private Date borrowedFrom;
    @CsvBindByPosition(position = 3)
    @CsvDate("dd/MM/yyyy")
    private Date borrowedUntil;
}
