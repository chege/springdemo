package springdemo.joke;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(
        path = "jokes"
)
class JokeController {
    private JokeService service;

    @RequestMapping(
            method = RequestMethod.GET
    )
    ResponseEntity<Joke> getRandomJoke() {
        Joke joke  = service.randomJoke();
        return ResponseEntity.ok(joke);
    }
}
