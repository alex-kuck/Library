package de.kucksdorf.library.persistence.util;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;

import lombok.Getter;

@Getter
public class UserCSV extends CSVBean {
    @CsvBindByPosition(position = 0)
    private String lastName;
    @CsvBindByPosition(position = 1)
    private String firstName;
    @CsvBindByPosition(position = 4)
    private String gender;
    @CsvBindByPosition(position = 2)
    @CsvDate("dd/MM/yyyy")
    private Date memberSince;
    @CsvBindByPosition(position = 3)
    @CsvDate("dd/MM/yyyy")
    private Date memberUntil;
}
