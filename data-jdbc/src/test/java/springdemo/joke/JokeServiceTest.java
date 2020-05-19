package springdemo.joke;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

/**
 * keywords: mock
 */
public class JokeServiceTest {

    @Test
    public void programmingJokeFromJoke() {
        Sv443 mockedSv443 = Mockito.mock(Sv443.class);
        doReturn(ResponseEntity.ok(new Sv443Joke("car", "type", null, "delivery", "some joke", new Flags(), 123, false)))
                .when(mockedSv443).findProgrammingJoke();

        Optional<Joke> actual = new JokeService(mockedSv443).programmingJoke();

        assertThat(actual)
                .isPresent()
                .contains(new Joke("some joke", "car"));
    }

    @Test
    public void programmingJokeFromSetupAndDelivery() {
        Sv443 mockedSv443 = Mockito.mock(Sv443.class);
        doReturn(ResponseEntity.ok(new Sv443Joke("car", "type", "some setup", "some delivery", null, new Flags(), 123, false)))
                .when(mockedSv443).findProgrammingJoke();

        Optional<Joke> actual = new JokeService(mockedSv443).programmingJoke();

        assertThat(actual)
                .isPresent()
                .contains(new Joke(format("some setup%ssome delivery", System.lineSeparator()), "car"));
    }

    @Test
    public void clientErrorExceptionThrown() {
        Sv443 mockedSv443 = Mockito.mock(Sv443.class);
        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST))
                .when(mockedSv443).findProgrammingJoke();

        Optional<Joke> actual = new JokeService(mockedSv443).programmingJoke();

        assertThat(actual).isNotPresent();
    }

    @Test
    public void responseEntityError() {
        Sv443 mockedSv443 = Mockito.mock(Sv443.class);
        doReturn(ResponseEntity.badRequest().build())
                .when(mockedSv443).findProgrammingJoke();

        Optional<Joke> actual = new JokeService(mockedSv443).programmingJoke();

        assertThat(actual).isNotPresent();
    }

    @Test
    public void emptyBody() {
        Sv443 mockedSv443 = Mockito.mock(Sv443.class);
        doReturn(ResponseEntity.ok().build())
                .when(mockedSv443).findProgrammingJoke();

        Optional<Joke> actual = new JokeService(mockedSv443).programmingJoke();

        assertThat(actual)
                .isNotPresent();

    }
}