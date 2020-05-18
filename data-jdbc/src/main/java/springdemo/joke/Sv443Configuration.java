package springdemo.joke;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * keywords: immutable
 */
@ConfigurationProperties("joke")
public class Sv443Configuration {

    private final String uri;

    @ConstructorBinding
    public Sv443Configuration(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

}
