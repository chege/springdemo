package springdemo.user;

import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * keywords: test slice, mock bean
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserRepo users;

    @Test
    public void getUser() throws Exception {
        given(this.users.findById(1L))
                .willReturn(Optional.of(new User(1L, "sodd", "sodd.sabeltann@sol.no")));

        this.mvc.perform(MockMvcRequestBuilders.get("/users/1")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void allUsers() throws Exception {
        given(this.users.findAll())
                .willReturn(Arrays.asList(
                        new User(1L, "Sodd", "sodd.sabeltann@sol.no"),
                        new User(2L, "Troender", "troender.barteussen@sol.no")
                ));

        this.mvc.perform(MockMvcRequestBuilders.get("/users")
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\"_embedded\":{\"userList\":[{\"id\":1,\"username\":\"Sodd\",\"_links\":{\"self\":{\"href\":\"http://localhost/users/1\"}}},{\"id\":2,\"username\":\"Troender\",\"_links\":{\"self\":{\"href\":\"http://localhost/users/2\"}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost/users\"}}}"));
    }

    @Test
    public void registerUser() throws Exception {
        given(this.users.existsByUserName("sodd"))
                .willReturn(false);
        given(this.users.save(ArgumentMatchers.any(User.class)))
                .willReturn(new User(33L, "sodd", "sodd.sabeltann@sol.no"));

        this.mvc.perform(MockMvcRequestBuilders.post("/users")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON)
                                               .content("{\"username\" : \"sodd\", \"email\":\"sodd.sabeltann@sol.no\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/users/33"));
    }

    @Test
    public void registerUserAlreadyExists() throws Exception {
        given(this.users.existsByUserName("sodd"))
                .willReturn(true);

        this.mvc.perform(MockMvcRequestBuilders.post("/users")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON)
                                               .content("{\"username\" : \"sodd\", \"email\":\"sodd.sabeltann@sol.no\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void deleteUser() throws Exception {
        given(this.users.existsById(1L))
                .willReturn(true);

        this.mvc.perform(MockMvcRequestBuilders.delete("/users/1")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deleteNonExistentUser() throws Exception {
        given(this.users.existsById(1L))
                .willReturn(false);

        this.mvc.perform(MockMvcRequestBuilders.delete("/users/1")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}
