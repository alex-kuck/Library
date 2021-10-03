package de.kucksdorf.library.persistence.util;

import com.opencsv.bean.CsvToBeanBuilder;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CSVReader {
    public static <T extends CSVBean> List<T> readEntries(Class<T> clazz, String fileName) throws FileNotFoundException {
        return new CsvToBeanBuilder(new FileReader(ResourceUtils.getFile("classpath:" + fileName))).withSkipLines(1)
                .withIgnoreEmptyLine(true)
                .withType(clazz)
                .build()
                .parse();
    }
}
