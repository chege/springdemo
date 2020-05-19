package springdemo.joke;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springdemo.LoggingRequestInterceptor;

/**
 * keywords: blocking
 */
@Service
class Sv443 {
    private final RestTemplate restTemplate;
    private final Sv443Configuration config;

    Sv443(RestTemplateBuilder builder, Sv443Configuration config) {
        this.restTemplate = builder.additionalInterceptors(new LoggingRequestInterceptor())
                                   .defaultHeader("User-Agent", "demo")
                                   .build();
        this.config = config;
    }

    ResponseEntity<Sv443Joke> findProgrammingJoke() {
        return restTemplate.getForEntity(config.getUri(), Sv443Joke.class);
    }
}