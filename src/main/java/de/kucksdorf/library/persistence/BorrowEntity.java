package de.kucksdorf.library.persistence;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

import de.kucksdorf.library.utils.DateUtils;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BorrowEntity {
    private String userName;
    private String bookName;
    private LocalDate borrowedFrom;
    private LocalDate borrowedUntil;

    public boolean belongsTo(UserEntity user) {
        return StringUtils.equals(userName(), user.getFullName());
    }

    private String userName() {
        String[] names = getUserName().split(",");
        return StringUtils.joinWith(", ", names[0].trim(), names[1].trim());
    }

    public boolean isActive() {
        return DateUtils.isActiveRange(borrowedFrom, borrowedUntil);
    }
}
