package springdemo.joke;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

/**
 * keywords: immutability, jsoncreator
 */
class Sv443Joke {
    private String category;
    private String type;
    private String setup;
    private String delivery;
    private String joke;
    private Flags flags;
    private Integer id;
    private boolean error;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    Sv443Joke(
            @JsonProperty("category") String category,
            @JsonProperty("type") String type,
            @JsonProperty("setup") String setup,
            @JsonProperty("delivery") String delivery,
            @JsonProperty("joke") String joke,
            @JsonProperty("flags") Flags flags,
            @JsonProperty("id") Integer id,
            @JsonProperty("error") boolean error) {
        this.category = category;
        this.type = type;
        this.setup = setup;
        this.delivery = delivery;
        this.joke = joke;
        this.flags = flags;
        this.id = id;
        this.error = error;
    }

    String getCategory() {
        return category;
    }

    String getType() {
        return type;
    }

    Optional<String> getSetup() {
        return Optional.ofNullable(setup);
    }

    Optional<String> getDelivery() {
        return Optional.ofNullable(delivery);
    }

    Optional<String> getJoke() {
        return Optional.ofNullable(joke);
    }

    Flags getFlags() {
        return flags;
    }

    Integer getId() {
        return id;
    }

    boolean isError() {
        return error;
    }
}
