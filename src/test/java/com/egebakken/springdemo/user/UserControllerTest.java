package com.egebakken.springdemo.user;

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

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserRepo users;

    @Test
    public void getUser() throws Exception {
        given(this.users.findById(1L))
                .willReturn(Optional.of(new User(1L, "sodd")));

        this.mvc.perform(MockMvcRequestBuilders.get("/user/1")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void allUsers() throws Exception {
        given(this.users.findAll())
                .willReturn(Arrays.asList(
                        new User(1L, "Sodd"),
                        new User(2L, "Troender")
                ));

        this.mvc.perform(MockMvcRequestBuilders.get("/user")
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"username\":\"Sodd\"},{\"id\":2,\"username\":\"Troender\"}]"));
    }

    @Test
    public void registerUser() throws Exception {
        given(this.users.existsByUserName("sodd"))
                .willReturn(false);
        given(this.users.save(ArgumentMatchers.any(User.class)))
                .willReturn(new User(33L, "sodd"));

        this.mvc.perform(MockMvcRequestBuilders.post("/user")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON)
                                               .content("{\"username\" : \"sodd\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/user/33"));
    }

    @Test
    public void registerUserAlreadyExists() throws Exception {
        given(this.users.existsByUserName("sodd"))
                .willReturn(true);

        this.mvc.perform(MockMvcRequestBuilders.post("/user")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON)
                                               .content("{\"username\" : \"sodd\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void deleteUser() throws Exception {
        given(this.users.existsById(1L))
                .willReturn(true);

        this.mvc.perform(MockMvcRequestBuilders.delete("/user/1")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deleteNonExistentUser() throws Exception {
        given(this.users.existsById(1L))
                .willReturn(false);

        this.mvc.perform(MockMvcRequestBuilders.delete("/user/1")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}
