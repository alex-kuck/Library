package de.kucksdorf.library.api;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;

import de.kucksdorf.library.domain.IUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/borrowedAtLeastOneBook")
    public ResponseEntity<Collection<User>> borrowedAtLeastOneBook() {
        return ResponseEntity.ok(userService.findUsersWhoBorrowedAtLeastOneBook());
    }

    @GetMapping("/activeWithoutActiveBorrow")
    public ResponseEntity<Collection<User>> activeWithoutActiveBorrow() {
        return ResponseEntity.ok(userService.findUsersWhoAreActiveAndBorrowNothing());
    }

    @GetMapping("/withActiveBorrowOn")
    public ResponseEntity<Collection<User>> withActiveBorrowOn(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(userService.findUsersWithBorrowOn(date));
    }
}
