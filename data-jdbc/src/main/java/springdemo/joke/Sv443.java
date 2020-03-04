package springdemo.joke;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class Sv443 {
    private RestTemplate restTemplate;

    public Sv443(RestTemplateBuilder builder, Sv443Configuration config) {
        builder.rootUri(config.getUri());
        this.restTemplate = builder.build();
    }
}
