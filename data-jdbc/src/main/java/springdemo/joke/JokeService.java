package springdemo.joke;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

/**
 * keywords: Inner model, only public class, private mapping, not exposing errors in this case
 */
@Service
public class JokeService {
    private static final Logger LOG = LoggerFactory.getLogger(JokeService.class);
    private final Sv443 sv443;

    public JokeService(Sv443 sv443) {
        this.sv443 = sv443;
    }

    public Optional<Joke> programmingJoke() {
        try {
            ResponseEntity<Sv443Joke> anyJoke = sv443.findProgrammingJoke();

            if (!anyJoke.getStatusCode().isError()) {
                Sv443Joke joke = anyJoke.getBody();

                if (joke != null) {
                    return Optional.of(joke)
                                   .map(this::toJoke);
                } else {
                    LOG.warn("Jokes are null");
                    return Optional.empty();
                }
            } else {
                LOG.error("Could not find joke: " + anyJoke.getStatusCode().getReasonPhrase());
                return Optional.empty();
            }
        } catch (RestClientException e) {
            LOG.error("Could not find joke", e);
            return Optional.empty();
        }
    }

    private Joke toJoke(Sv443Joke sv443Joke) {
        return new Joke(
                sv443Joke.getJoke()
                         .orElseGet(
                                 () -> Stream.of(sv443Joke.getSetup(), sv443Joke.getDelivery())
                                             .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                                             .collect(Collectors.joining(System.lineSeparator()))
                         ),
                sv443Joke.getCategory()
        );
    }
}
