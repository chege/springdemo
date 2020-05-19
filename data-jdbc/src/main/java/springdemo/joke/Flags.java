package springdemo.joke;

import com.fasterxml.jackson.annotation.JsonProperty;

class Flags {
    private boolean nsfw;
    private boolean religious;
    private boolean political;
    private boolean racist;
    private boolean sexist;

    public Flags(
            @JsonProperty("nsfw") boolean nsfw,
            @JsonProperty("religious") boolean religious,
            @JsonProperty("political") boolean political,
            @JsonProperty("racist") boolean racist,
            @JsonProperty("sexist") boolean sexist
    ) {
        this.nsfw = nsfw;
        this.religious = religious;
        this.political = political;
        this.racist = racist;
        this.sexist = sexist;
    }

    boolean isNsfw() {
        return nsfw;
    }

    boolean isReligious() {
        return religious;
    }

    boolean isPolitical() {
        return political;
    }

    boolean isRacist() {
        return racist;
    }

    boolean isSexist() {
        return sexist;
    }
}
