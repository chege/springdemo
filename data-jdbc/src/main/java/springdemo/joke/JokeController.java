package springdemo.joke;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Keywords: HTTP verbs, HTTP error codes
 */
@RestController
@RequestMapping(
        path = "jokes"
)
class JokeController {
    private final JokeService service;

    JokeController(JokeService service) {
        this.service = service;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    ResponseEntity<Joke> getRandomJoke() {
        return service.programmingJoke()
                      .map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound()
                                                     .build());
    }
}
