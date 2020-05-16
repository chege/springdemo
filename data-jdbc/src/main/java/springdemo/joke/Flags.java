package springdemo.joke;

class Flags {
    private boolean nsfw;
    private boolean religious;
    private boolean political;
    private boolean racist;
    private boolean sexist;

    boolean isNsfw() {
        return nsfw;
    }

    void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    boolean isReligious() {
        return religious;
    }

    void setReligious(boolean religious) {
        this.religious = religious;
    }

    boolean isPolitical() {
        return political;
    }

    void setPolitical(boolean political) {
        this.political = political;
    }

    boolean isRacist() {
        return racist;
    }

    void setRacist(boolean racist) {
        this.racist = racist;
    }

    boolean isSexist() {
        return sexist;
    }

    void setSexist(boolean sexist) {
        this.sexist = sexist;
    }
}
