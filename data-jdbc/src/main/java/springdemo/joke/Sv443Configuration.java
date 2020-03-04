package springdemo.joke;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("joke")
class Sv443Configuration {
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
