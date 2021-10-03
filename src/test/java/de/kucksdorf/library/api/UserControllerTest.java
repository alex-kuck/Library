package de.kucksdorf.library.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import de.kucksdorf.library.domain.IUserService;
import de.kucksdorf.library.domain.enums.Gender;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { UserController.class })
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserService userService;

    private final User user1 = user("Hans", "Wurst", Gender.MALE);
    private final User user2 = user("Leia", "Skywalker", Gender.FEMALE);

    private User user(String firstName, String lastName, Gender gender) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .memberSince(LocalDate.of(2000, 1, 1))
                .memberUntil(LocalDate.of(2999, 12, 31))
                .build();
    }

    @Test
    void borrowedAtLeastOneBook() throws Exception {
        when(userService.findUsersWhoBorrowedAtLeastOneBook()).thenReturn(Set.of(user2, user1));
        mockMvc.perform(get("/users/borrowedAtLeastOneBook").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(objectMapper.writeValueAsString(Set.of(user1, user2))));
    }

    @Test
    void activeWithoutActiveBorrow() throws Exception {
        when(userService.findUsersWhoAreActiveAndBorrowNothing()).thenReturn(Set.of(user1));
        mockMvc.perform(get("/users/activeWithoutActiveBorrow").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(objectMapper.writeValueAsString(Set.of(user1))));
    }

    @Test
    void withActiveBorrowOnDate() throws Exception {
        LocalDate date = LocalDate.of(2020,1,1);
        when(userService.findUsersWithBorrowOn(eq(date))).thenReturn(Set.of(user2));
        mockMvc.perform(get("/users/withActiveBorrowOn?date=" + date).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(objectMapper.writeValueAsString(Set.of(user2))));
    }
}