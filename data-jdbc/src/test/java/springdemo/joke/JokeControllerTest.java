package springdemo.joke;

import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = JokeController.class)
public class JokeControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    JokeService jokes;

    @Test
    public void randomJoke() throws Exception {
        given(this.jokes.programmingJoke())
                .willReturn(Optional.of(new Joke("I invented a new word! Plagiarism!", "new")));

        this.mvc.perform(MockMvcRequestBuilders.get("/jokes")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"joke\":\"I invented a new word! Plagiarism!\",\"category\":\"new\"}"));

    }

    @Test
    public void noJoke() throws Exception {
        given(this.jokes.programmingJoke())
                .willReturn(Optional.empty());

        this.mvc.perform(MockMvcRequestBuilders.get("/jokes")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}