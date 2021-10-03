package de.kucksdorf.library.persistence.util;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Getter;

@Getter
public class BookCSV extends CSVBean {
    @CsvBindByPosition(position = 0)
    private String title;
    @CsvBindByPosition(position = 1)
    private String author;
    @CsvBindByPosition(position = 2)
    private String genre;
    @CsvBindByPosition(position = 3)
    private String publisher;
}
