package pl.edu.pk.backend;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pk.backend.database.Card;
import pl.edu.pk.backend.database.Role;
import pl.edu.pk.backend.database.User;
import pl.edu.pk.backend.database.repos.CardRepo;
import pl.edu.pk.backend.database.repos.UserRepo;
import pl.edu.pk.backend.controllers.RestHandler;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import pl.edu.pk.backend.responses.Response;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CahApplicationTests {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private RestHandler restHandler;

    @Test
    @Order(1)
    public void Database_DeleteAll_User() {
        userRepo.deleteAll();
        assertThat(userRepo.findAll()).hasSize(0);
    }

    @Test
    @Order(2)
    public void Database_Create_User() {
        User user = new User();
        user.user = "user";
        user.pass = "user";
        user.role = Role.User;
        userRepo.save(user);
        assertThat(userRepo.findByUser("user")).isNotNull();
    }

    @Test
    @Order(3)
    public void Database_Create_Admin() {
        User admin = new User();
        admin.user = "admin";
        admin.pass = "admin";
        admin.role = Role.Admin;
        userRepo.save(admin);
        assertThat(userRepo.findByUser("admin")).isNotNull();
    }

    @Test
    @Order(4)
    public void RestHandler_GetMe_Correct() {
        User user = userRepo.findByUser("user");
        Response response = Objects.requireNonNull(restHandler.GetMe(user).getBody());
        assertThat(response.code).isEqualTo(200);
    }

    @Test
    @Order(5)
    public void RestHandler_GetMe_Failed() {
        Response response = Objects.requireNonNull(restHandler.GetMe(new User()).getBody());
        assertThat(response.code).isEqualTo(400);
        assertThat(response.message).isEqualTo("User not found");
    }

    @Test
    @Order(6)
    public void RestHandler_GetUser_Correct() {
        User user = userRepo.findByUser("user");
        Response response = Objects.requireNonNull(restHandler.GetUser(user.id).getBody());
        assertThat(response.code).isEqualTo(200);
        assertThat(response.message).isEqualTo("user");
    }

    @Test
    @Order(7)
    public void RestHandler_GetUser_Failed() {
        Response response = Objects.requireNonNull(restHandler.GetUser("").getBody());
        assertThat(response.code).isEqualTo(400);
        assertThat(response.message).isEqualTo("User not found");
    }

    @Test
    @Order(8)
    public void RestHandler_DeleteUser_Correct() {
        User user = userRepo.findByUser("user");
        Response response = Objects.requireNonNull(restHandler.DeleteUser(user.id).getBody());
        assertThat(response.code).isEqualTo(200);
        assertThat(response.message).isEqualTo("OK");
    }

    @Test
    @Order(9)
    public void RestHandler_DeleteUser_Failed() {
        Response response = Objects.requireNonNull(restHandler.DeleteUser("").getBody());
        assertThat(response.code).isEqualTo(400);
        assertThat(response.message).isEqualTo("User not found");
    }

    @Test
    @Order(10)
    public void RestHandler_GetCard_Correct() {
        Card card = cardRepo.findAll().stream().findAny().get();
        Response response = Objects.requireNonNull(restHandler.GetCard(card.id).getBody());
        assertThat(response.code).isEqualTo(200);
    }

    @Test
    @Order(11)
    public void RestHandler_GetCard_Failed() {
        Response response = Objects.requireNonNull(restHandler.GetCard("").getBody());
        assertThat(response.code).isEqualTo(400);
        assertThat(response.message).isEqualTo("User not found");
    }
}